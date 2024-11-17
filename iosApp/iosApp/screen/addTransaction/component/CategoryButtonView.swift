import SwiftUI
import Shared

struct CategoryButtonView: View {
    
    let uiState: AddTransactionState
    let onEvent: (AddTransactionEvent) -> Void
    
    var body: some View {
        Button(
            action: { onEvent(.Sheet(shown: true)) },
            label: {
                HStack(alignment: .center, spacing: 16) {
                    Text(uiState.category.name).foregroundStyle(ColorTheme.outline)
                    Spacer()
                    Image(systemName: "square.grid.2x2").foregroundStyle(ColorTheme.outline)
                }
                .padding(16)
                .background(RoundedRectangle(cornerRadius: 16).stroke(ColorTheme.outline, lineWidth: 2))
            }
        ).buttonStyle(PlainButtonStyle())
    }
}

#Preview {
    CategoryButtonView(uiState: AddTransactionState(), onEvent: { _ in })
}
