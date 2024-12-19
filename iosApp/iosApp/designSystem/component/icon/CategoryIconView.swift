import SwiftUI

struct CategoryIconView: View {
    let icon: String
    let color: Color
    var size: CGFloat = 48
    var padding: CGFloat = 12
    
    private var contentColor: Color {
        let uiColor = UIColor(color)
        var brightness: CGFloat = 0
        uiColor.getWhite(&brightness, alpha: nil)
        return brightness < 0.7 ? .white : .black
    }
    
    var body: some View {
        Circle()
            .fill(color)
            .overlay(
                Image(systemName: icon)
                    .resizable()
                    .scaledToFit()
                    .padding(padding)
                    .foregroundColor(contentColor)
            )
            .frame(width: size, height: size)
    }
}

#Preview {
    CategoryIconView(icon: IconConstant.Airplane, color: ColorTheme.primary)
}
