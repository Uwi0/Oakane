import SwiftUI

struct DisplayImageFileView: View {
    
    var defaultImage: String = ""
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
            loadSavedImage()
        }
    }
    
    private func loadSavedImage() {
        if let fileUrl = FileManager.default.getSavedImageURL(fileName: fileName) {
            DispatchQueue.global(qos: .background).async {
                do {
                    let data = try Data(contentsOf: fileUrl)
                    if let loadedImage = UIImage(data: data) {
                        DispatchQueue.main.async {
                            self.uiImage = loadedImage
                        }
                    } else {
                        print("Failed to create UIImage from data")
                    }
                } catch {
                    print("Failed to load data from file: \(error.localizedDescription)")
                }
            }
        } else {
            print("Image file not found")
        }
    }
}

#Preview {
    DisplayImageFileView(
        defaultImage: ImageConstants.defaultImage,fileName: "", width: 68, height: 68)
}
