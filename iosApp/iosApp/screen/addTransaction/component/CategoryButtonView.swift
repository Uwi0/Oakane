import SwiftUI
import Shared

struct CategoryButtonView: View {
    
    let uiState: AddTransactionState
    let onEvent: (AddTransactionEvent) -> Void
    
    var body: some View {
        Button(
            action: { onEvent(.Sheet(shown: true)) },
            label: {
                VStack(alignment: .leading, spacing: 8) {
                    Text("Category")
                    CategoryButtonContent()
                }
            }
        ).buttonStyle(PlainButtonStyle())
    }
    
    @ViewBuilder
    private func CategoryButtonContent() -> some View {
        HStack(alignment: .center, spacing: 16) {
            Text(uiState.category.name).foregroundStyle(ColorTheme.outline)
            Spacer()
            Image(systemName: "square.grid.2x2").foregroundStyle(ColorTheme.outline)
        }
        .outlinedTextStyle(borderColor: ColorTheme.outline)
    }
}

#Preview {
    CategoryButtonView(uiState: AddTransactionState(), onEvent: { _ in })
}
