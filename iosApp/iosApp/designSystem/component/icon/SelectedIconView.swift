import SwiftUI
import Shared

struct SelectedIconView: View {
    
    let imageName: String
    let icon: CategoryIconName
    let color: Color
    var size: CGFloat = 48
    var padding: CGFloat = 12
    
    private var isDefaultIcon: Bool {
        imageName.isEmpty || icon.displayName.lowercased() == imageName.lowercased()
    }
    
    private var iconSystem: String {
        icon.asIconCategory()
    }
    
    var body: some View {
        VStack{
            if isDefaultIcon {
                CategoryIconView(icon: iconSystem, color: color, size: size, padding: padding)
            } else {
                DisplayImageFileView(fileName: imageName, width: size, height: size)
                    .overlay{ Circle().stroke(color, lineWidth: 3) }
            }
        }
    }
}

#Preview {
    SelectedIconView(imageName: "", icon: .books, color: Color.accentColor, size: 48, padding: 12)
}
