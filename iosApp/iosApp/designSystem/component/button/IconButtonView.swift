import SwiftUI

struct IconButtonView: View {
    
    let name: String
    let width: CGFloat
    let onClick: () -> Void
    var fontWeight: Font.Weight = .regular
    
    var body: some View {
        Button(
            action: onClick,
            label: {
                Image(systemName: name)
                    .resizable()
                    .frame(width: width, height: width)
                    .fontWeight(fontWeight)
                    .foregroundStyle(ColorTheme.outline)
            }
        )
    }
}

#Preview {
    IconButtonView(name: "pencil", width: 24, onClick: {})
}
