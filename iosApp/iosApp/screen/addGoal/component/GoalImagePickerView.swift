import SwiftUI
import PhotosUI

struct GoalImagePickerView: View {
    
    let imageUrl: String
    let onSelectedFile: (String) -> Void
    
    @State private var selectedItem: PhotosPickerItem? = nil
    @State private var selectedImage: UIImage? = nil
    private let size: CGFloat = 120
    
    var body: some View {
        PhotosPicker(
            selection: $selectedItem,
            matching: .images,
            photoLibrary: .shared()
        ){
            SelectedGoalImageView(
                imageUrl: imageUrl,
                selectedImage: selectedImage
            )
        }
        .onChange(of: selectedItem, perform: displayImage(photoPicker:))
        .onChange(of: imageUrl, perform: displayImage(url:))
    }
    
    private func displayImage(photoPicker: PhotosPickerItem?) {
        Task {
            if let data = try? await photoPicker?.loadTransferable(type: Data.self),
               let uiImage = UIImage(data: data) {
                selectedImage = uiImage
                let savedImage = saveImageToFileSystem(image: uiImage)
                switch savedImage {
                case .success(let imageName):
                    onSelectedFile(imageName)
                case .failure(let error):
                    print(error)
                }
            }
        }
    }
    
    private func displayImage(url: String) {
        guard let fileUrl = FileManager.default.getSavedImageURL(fileName: url) else {
            print("Image file not found")
            return
        }
        
        DispatchQueue.global(qos: .background).async {
            do {
                let data = try Data(contentsOf: fileUrl)
                guard let loadedImage = UIImage(data: data) else {
                    print("Failed to create UIImage from data")
                    return
                }
                
                DispatchQueue.main.async {
                    self.selectedImage = loadedImage
                }
            } catch {
                print("Failed to load data from file: \(error.localizedDescription)")
            }
        }
    }
}

private struct SelectedGoalImageView: View {
    
    let imageUrl: String
    var selectedImage: UIImage? = nil
    
    private let size: CGFloat = 120
    
    var body: some View {
        ZStack {
            
            if let uiImage = selectedImage {
                Image(uiImage: uiImage)
                    .resizable()
                    .scaledToFill()
                    .frame(width: size, height: size)
                    .clipShape(Circle())
            }
            else {
                Image(ImageConstants.imageAddGoalDefault)
                    .resizable()
                    .scaledToFit()
                    .frame(width: size, height: size)
                    .clipShape(Circle())
            }
            
            IconImagePicker()
                .frame(width: size * 0.25, height: size * 0.25)
                .offset(x: size * 0.35, y: size * 0.35)
        }
        .frame(width: size, height: size)
    }
}

private struct IconImagePicker: View {
    var body: some View {
        ZStack {
            Circle()
                .fill(ColorTheme.primary) // Background for the icon
                .shadow(radius: 2)
            
            Image(systemName: "camera")
                .foregroundColor(ColorTheme.onPrimary)
                .font(.system(size: 16))
        }
    }
}
    
#Preview {
    GoalImagePickerView(imageUrl: "",onSelectedFile: { name in })
}
