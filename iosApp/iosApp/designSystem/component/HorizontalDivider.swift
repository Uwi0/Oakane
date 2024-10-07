import SwiftUI

struct HorizontalDivider: View {
    var color: Color = ColorTheme.outline
    var width: CGFloat = 2
    var body: some View {
        Rectangle()
            .fill(color)
            .frame(height: width)
            .edgesIgnoringSafeArea(.horizontal)
    }
}

#Preview {
    HorizontalDivider(color: .red)
}
