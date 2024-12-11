import SwiftUI

struct TextButtonView: View {
    let title: String
    let onClick: () -> Void
    var body: some View {
        Button(action: onClick) {
            Text(title)
                .font(Typography.labelLarge)
                .foregroundStyle(ColorTheme.primary)
        }
        .buttonStyle(PlainButtonStyle())
    }
}

#Preview {
    TextButtonView(title: "Hello", onClick: {})
}
