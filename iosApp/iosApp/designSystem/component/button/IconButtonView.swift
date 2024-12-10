import SwiftUI

struct IconButtonView: View {
    
    let name: String
    let size: CGFloat
    let onClick: () -> Void
    var fontWeight: Font.Weight = .regular
    
    var body: some View {
        Button(
            action: onClick,
            label: {
                Image(systemName: name)
                    .resizable()
                    .frame(width: size, height: size)
                    .fontWeight(fontWeight)
                    .foregroundStyle(ColorTheme.outline)
            }
        )
    }
}

#Preview {
    IconButtonView(name: "pencil", size: 24, onClick: {})
}
