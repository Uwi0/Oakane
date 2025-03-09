import SwiftUI

struct IconButtonView: View {
    
    let name: String
    let width: CGFloat
    let onClick: () -> Void
    
    var body: some View {
        Button(
            action: onClick,
            label: {
                Image(systemName: name)
                    .resizable()
                    .scaledToFit()
                    .frame(width: width, height: width)
                    .foregroundStyle(ColorTheme.outline)
            }
        )
    }
}

#Preview {
    IconButtonView(name: "pencil", width: 24, onClick: {})
}
