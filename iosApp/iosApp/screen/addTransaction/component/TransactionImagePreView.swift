import SwiftUI

struct TransactionImagePreView: View {
    
    let imageUrl: String
    let onDismiss: () -> Void
    @State private var uiImage: UIImage? = nil
    
    var body: some View {
        ZStack(alignment: .topTrailing) {
            ImageDisplay()
            CloseButton()
        }
        .frame(maxWidth: .infinity, maxHeight: 144)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .onAppear {
            loadImageFromAlbum()
        }
        .onChange(of: imageUrl) {
            loadImageFromAlbum()
        }
    }
    
    @ViewBuilder
    private func ImageDisplay() -> some View {
        if let safeImage = uiImage {
            Image(uiImage: safeImage).resizable()
        } else {
            Image(ImageConstants.fubukiStare).resizable()
        }
    }
    
    @ViewBuilder
    private func CloseButton() -> some View {
        Button(
            action: onDismiss,
            label: {
                Image(systemName: "xmark")
                    .tint(ColorTheme.onError)
                    .imageScale(.small)
                    .frame(width: 32, height: 32)
                    .background(ColorTheme.error)
                    .clipShape(Circle())
            }
        )
        .padding(.vertical, 12)
        .padding(.horizontal, 14)
    }
    
    private func loadImageFromAlbum() {
        Task {
            uiImage = await fetchImage(localIdentifier: imageUrl)
        }
    }
}

#Preview {
    TransactionImagePreView(imageUrl: "Hello world"){}
}
