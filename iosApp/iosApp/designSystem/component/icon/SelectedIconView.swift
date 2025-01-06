import SwiftUI
import Shared

struct SelectedIconView: View {
    
    let imageName: String
    let icon: CategoryIconName
    let color: Int64
    var size: CGFloat = 48
    var padding: CGFloat = 12
    
    private var isDefaultIcon: Bool {
        !imageName.contains("jpg")
    }
    
    private var iconSystem: String {
        icon.asIconCategory()
    }
    
    private var formattedColor: Color {
        Color(hex: color)
    }
    
    var body: some View {
        VStack{
            if isDefaultIcon {
                CategoryIconView(icon: iconSystem, color: formattedColor, size: size, padding: padding)
            } else {
                DisplayImageFileView(fileName: imageName, width: size, height: size)
                    .overlay{ Circle().stroke(formattedColor, lineWidth: 3) }
            }
        }
    }
}
