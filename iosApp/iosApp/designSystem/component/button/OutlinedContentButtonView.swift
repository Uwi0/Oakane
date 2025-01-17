import SwiftUI

struct OutlinedContentButtonView<Content: View>: View {
    
    @ViewBuilder var content: Content
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            HStack(alignment: .center, spacing: 16) {
                content.font(Typography.titleMedium)
                    .foregroundStyle(ColorTheme.primary)
            }
            .foregroundStyle(ColorTheme.primary)
            .padding(.vertical, 16)
            .padding(.horizontal, 16)
            .background(RoundedRectangle(cornerRadius: 16).stroke(ColorTheme.primary, lineWidth: 2))
        }
        .buttonStyle(.plain)
    }
}

extension OutlinedContentButtonView {
    public init(
        onClick: @escaping () -> Void,
        @ViewBuilder content: () -> Content
    ) {
        self.content = content()
        self.onClick = onClick
    }
}

#Preview {
    OutlinedContentButtonView(onClick: {}, content: { Text("Hello world")})
}
