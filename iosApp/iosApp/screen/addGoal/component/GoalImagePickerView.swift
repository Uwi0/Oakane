import SwiftUI
import PhotosUI

struct GoalImagePickerView: View {
    
    @State private var selectedItem: PhotosPickerItem? = nil
    @State private var selectedImage: UIImage? = nil
    private let size: CGFloat = 120
    
    var body: some View {
        PhotosPicker(
            selection: $selectedItem,
            matching: .images,
            photoLibrary: .shared()
        ){
            ZStack {
                if let uiImage = selectedImage {
                    Image(uiImage: uiImage)
                        .resizable()
                        .scaledToFill()
                        .frame(width: size, height: size)
                        .clipShape(Circle())
                } else {
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
        .onChange(of: selectedItem) { newItem in
            Task {
                if let data = try? await newItem?.loadTransferable(type: Data.self),
                   let uiImage = UIImage(data: data) {
                    selectedImage = uiImage
                    let savedImage = saveImageToFileSystem(image: uiImage)
                    switch savedImage {
                    case .success(let imageName):
                        print("success")
                    case .failure(let error):
                        print(error)
                    }
                }
            }
        }
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
    GoalImagePickerView()
}
