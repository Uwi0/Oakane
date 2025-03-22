import Foundation
import Photos
import UIKit

func saveImageToFileSystem(image: UIImage) -> Result<String, Error> {
    let fileName = "image-oakane\(Date().timeIntervalSince1970).jpg"
    let fileUrl = FileManager.default.getDocumentsDirectory().appendingPathComponent(fileName)

    do {
        if let data = image.jpegData(compressionQuality: 1.0) {
            try data.write(to: fileUrl)
            return .success(fileUrl.lastPathComponent)
        } else {
            return .failure(NSError(domain: "ImageConversionError", code: 1, userInfo: nil))
        }
    } catch {
        return .failure(error)
    }
}

func saveImageToPhotoLibrary(image: UIImage, albumName: String = "Oakane", completion: @escaping (Result<String, Error>) -> Void) {
    PHPhotoLibrary.requestAuthorization { status in
        guard status == .authorized else {
            completion(.failure(NSError(domain: "PhotoLibraryError", code: 1, userInfo: [NSLocalizedDescriptionKey: "Photo library access denied"])))
            return
        }
        
        let photoLibrary = PHPhotoLibrary.shared()
        
        // Fetch or create album
        let fetchOptions = PHFetchOptions()
        fetchOptions.predicate = NSPredicate(format: "title = %@", albumName)
        let existingAlbums = PHAssetCollection.fetchAssetCollections(with: .album, subtype: .any, options: fetchOptions)
        
        var album: PHAssetCollection?
        
        if let existingAlbum = existingAlbums.firstObject {
            album = existingAlbum
            saveImageToAlbum(image: image, album: existingAlbum, photoLibrary: photoLibrary, completion: completion)
        } else {
            var albumPlaceholder: PHObjectPlaceholder?
            
            photoLibrary.performChanges {
                let createAlbumRequest = PHAssetCollectionChangeRequest.creationRequestForAssetCollection(withTitle: albumName)
                albumPlaceholder = createAlbumRequest.placeholderForCreatedAssetCollection
            } completionHandler: { success, error in
                guard success, let placeholder = albumPlaceholder else {
                    completion(.failure(NSError(domain: "PhotoLibraryError", code: 2, userInfo: [NSLocalizedDescriptionKey: "Failed to create album"])))
                    return
                }
                
                let fetchResult = PHAssetCollection.fetchAssetCollections(withLocalIdentifiers: [placeholder.localIdentifier], options: nil)
                album = fetchResult.firstObject
                saveImageToAlbum(image: image, album: album!, photoLibrary: photoLibrary, completion: completion)
            }
        }
    }
}

func saveImageToAlbum(image: UIImage, album: PHAssetCollection, photoLibrary: PHPhotoLibrary, completion: @escaping (Result<String, Error>) -> Void) {
    photoLibrary.performChanges {
        let assetRequest = PHAssetChangeRequest.creationRequestForAsset(from: image)
        let albumChangeRequest = PHAssetCollectionChangeRequest(for: album)
        
        guard let placeholder = assetRequest.placeholderForCreatedAsset else { return }
        albumChangeRequest?.addAssets([placeholder] as NSArray)
    } completionHandler: { success, error in
        if let error = error {
            completion(.failure(error))
        } else {
            let fetchOptions = PHFetchOptions()
            let assets = PHAsset.fetchAssets(in: album, options: fetchOptions)
            if let lastAsset = assets.lastObject {
                completion(.success(lastAsset.localIdentifier))
            } else {
                completion(.failure(NSError(domain: "PhotoLibraryError", code: 3, userInfo: [NSLocalizedDescriptionKey: "Failed to retrieve saved image"])))
            }
        }
    }
}

func fetchImage(localIdentifier: String) async -> UIImage? {
    let fetchOptions = PHFetchOptions()
    let assets = PHAsset.fetchAssets(withLocalIdentifiers: [localIdentifier], options: fetchOptions)
    
    guard let asset = assets.firstObject else { return nil }
    
    return await withCheckedContinuation { continuation in
        let manager = PHImageManager.default()
        let options = PHImageRequestOptions()
        options.version = .current
        options.isSynchronous = false
        options.deliveryMode = .highQualityFormat
        
        var hasResumed = false
        
        manager.requestImage(
            for: asset,
            targetSize: PHImageManagerMaximumSize,
            contentMode: .aspectFit,
            options: options) { (image, _) in
                guard !hasResumed else { return }
                hasResumed = true
                continuation.resume(returning: image)
            }
    }
}

extension FileManager {
    func getDocumentsDirectory() -> URL {
        return urls(for: .documentDirectory, in: .userDomainMask)[0]
    }

    func getSavedImageURL(fileName: String) -> URL? {
        let fileURL = getDocumentsDirectory().appendingPathComponent(fileName)
        return fileExists(atPath: fileURL.path) ? fileURL : nil
    }
}
