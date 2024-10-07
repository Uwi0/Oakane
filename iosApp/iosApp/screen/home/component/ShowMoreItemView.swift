import SwiftUI

struct ShowMoreItemView: View {
    let onClick: () -> Void
    var body: some View {
        HStack {
            Spacer()
            Button(
                action: onClick,
                label: {
                    Text("Show More")
                        .font(Typography.labelLarge)
                        .foregroundStyle(ColorTheme.primary)
                }
            )
            Spacer()
        }
    }
}

#Preview {
    ShowMoreItemView {
        
    }
}
