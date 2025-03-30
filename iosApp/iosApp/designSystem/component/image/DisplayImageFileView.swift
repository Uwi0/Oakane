import SwiftUI

struct DisplayImageFileView: View {
    
    var defaultImage: String = ImageConstants.oakaneIcon
    let fileName: String
    let width: CGFloat
    let height: CGFloat
    
    @State private var uiImage: UIImage? = nil
    
    var body: some View {
        VStack {
            if let uiImage = uiImage {
                Image(uiImage: uiImage)
                    .resizable()
                    .scaledToFit()
                    .frame(width: width, height: height)
                    .clipShape(Circle())
            } else {
                Image(defaultImage)
                    .resizable()
                    .scaledToFit()
                    .frame(width: width, height: height)
                    .clipShape(Circle())
            }
        }
        .onAppear {
            if !fileName.isEmpty {
                loadSavedImage(imageName: fileName)
            }
        }
        .onChange(of: fileName) {
            if !fileName.isEmpty {
                loadSavedImage(imageName: fileName)
            }
        }
    }
    
    private func loadSavedImage(imageName: String) {
        Task {
            uiImage = await fetchImage(localIdentifier: fileName)
        }
    }
}

#Preview {
    DisplayImageFileView(
        defaultImage: ImageConstants.oakaneIcon,
        fileName: "",
        width: 68,
        height: 68
    )
}
