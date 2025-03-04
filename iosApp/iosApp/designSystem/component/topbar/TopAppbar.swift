import SwiftUI

struct TopAppbar: View {
    
    let title: String
    
    var body: some View {
        HStack {
            Text(title)
                .foregroundStyle(ColorTheme.onSurface)
                .font(Typography.titleLarge)
        }
        .padding(.vertical, 8)
        .padding(.horizontal, 16)
        .frame(height: 64)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(ColorTheme.surface)
    }
}

#Preview {
    ZStack {
        ColorTheme.outline.ignoresSafeArea(edges: .all)
        TopAppbar(title: "Hello world")
    }
}
