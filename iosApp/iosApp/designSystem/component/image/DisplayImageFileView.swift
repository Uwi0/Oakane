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
            if !fileName.isEmpty {
                loadSavedImage(imageName: fileName)
            }
        }
        .onChange(of: fileName) { newImage in
            if !newImage.isEmpty {
                loadSavedImage(imageName: newImage)
            }
        }
    }
    
    private func loadSavedImage(imageName: String) {
        print("Loading image: \(imageName)")
        guard let fileUrl = FileManager.default.getSavedImageURL(fileName: imageName) else {
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
                    self.uiImage = loadedImage
                }
            } catch {
                print("Failed to load data from file: \(error.localizedDescription)")
            }
        }
    }
}

#Preview {
    DisplayImageFileView(
        defaultImage: ImageConstants.defaultImage,fileName: "", width: 68, height: 68)
}
