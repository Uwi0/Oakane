import SwiftUI

struct ShowMoreItemView: View {
    let onClick: () -> Void
    
    var body: some View {
        HStack {
            ButtonView()
        }
        .frame(maxWidth: .infinity, alignment: .center)
    }
    
    @ViewBuilder
    private func ButtonView() -> some View {
        Button(
            action: onClick,
            label: {
                TextButton()
            }
        )
    }
    
    @ViewBuilder
    private func TextButton() -> some View {
        Text("Show More")
            .font(Typography.labelLarge)
            .foregroundStyle(ColorTheme.primary)
    }
}

#Preview {
    ShowMoreItemView {
        
    }
}
