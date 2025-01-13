import SwiftUI

struct FilledContentButtonView<Content: View>: View {
    
    @ViewBuilder let content: Content
    let onclick: () -> Void
    
    var body: some View {
        Button(action: onclick) {
            HStack(spacing: 16) {
                content
                    .font(Typography.titleMedium)
                    .foregroundStyle(ColorTheme.onPrimary)
            }
            .padding(.vertical, 16)
            .padding(.horizontal, 16)
            .frame(maxWidth: .infinity, alignment: .center)
            .background(
                RoundedRectangle(cornerRadius: 16)
                    .fill(ColorTheme.primary)
            )
        }
        .buttonStyle(PlainButtonStyle())
    }
}

extension FilledContentButtonView {
    init(
        onclick: @escaping () -> Void,
        @ViewBuilder content: () -> Content
    ) {
        self.content = content()
        self.onclick = onclick
    }
}

#Preview {
    FilledContentButtonView(
        onclick: {},
        content: { Text("Hello") }
    )
}
