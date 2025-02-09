import SwiftUI
import PhotosUI

struct ButtonPickImage: View {
    
    @State private var selectedItem: PhotosPickerItem? = nil
    @State private var selectedImage: UIImage? = nil
    
    var body: some View {
        PhotosPicker(
            selection: $selectedItem,
            matching: .images,
            photoLibrary: .shared()
        ) {
            OutlinedContentButtonView(
                onClick: {},
                content: {
                    Text("Gallery")
                    Spacer()
                    Image(systemName: "photo")
                }
            )
        }
        .onChange(of: selectedItem) {
            getImageLocalIdentifier()
        }
    }
    
    func getImageLocalIdentifier() {
        Task {
            if let selectedItem {
                do {
                    if let imageData = try await selectedItem.loadTransferable(type: Data.self),
                       let image = UIImage(data: imageData) {
                        selectedImage = image
                        saveImageToPhotoLibrary(image: image) { result in
                            switch result {
                            case .success(let message):
                                print(message)
                            case .failure(let error):
                                print("Error saving image: \(error.localizedDescription)")
                            }
                        }
                    }
                } catch {
                    print("Error loading image: \(error.localizedDescription)")
                }
            }
        }
    }
}

#Preview {
    ButtonPickImage()
}
