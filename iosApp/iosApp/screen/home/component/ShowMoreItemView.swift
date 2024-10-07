import SwiftUI

struct ShowMoreItemView: View {
    var body: some View {
        HStack {
            Button(
                action:{},
                label: {
                    Text("Show More")
                        .font(Typography.labelLarge)
                        .foregroundStyle(ColorTheme.primary)
                }
            )
        }
    }
}

#Preview {
    ShowMoreItemView()
}
