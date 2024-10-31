import SwiftUI

struct CategoryIconView: View {
    let icon: String
    let color: Color
    
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
                    .padding(12)
                    .foregroundColor(contentColor)
            )
            .frame(width: 48, height: 48)
    }
}

#Preview {
    CategoryIconView(icon: IconConstant.Airplane, color: ColorTheme.primary)
}
