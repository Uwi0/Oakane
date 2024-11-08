import Foundation
import UIKit

func saveImageToFileSystem(image: UIImage) -> Result<String, Error>{
    let fileName = "image-oakane\(Date().timeIntervalSince1970).jpg"
    let fileUrl = FileManager.default.temporaryDirectory.appendingPathComponent(fileName)
    
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
