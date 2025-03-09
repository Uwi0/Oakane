import SwiftUI
import Shared

struct FilterByTypeView: View {
    
    let uiState: TransactionsState
    let onEvent: (TransactionsEvent) -> Void
    
    var body: some View {
        VStack(alignment: .leading,spacing: 16) {
            
            Text("Filter by Type")
                .font(Typography.titleSmall)
            
            ScrollView(.horizontal) {
                HStack(alignment: .firstTextBaseline, spacing: 16) {
                    ForEach(TransactionType.allCases, id: \.self){ type in
                        let isSelected = uiState.selectedType == type
                        InputChipView(
                            title: type.name,
                            isSelected: isSelected,
                            onSelected: {onEvent(.FilterByType(value: type)) }
                        )
                    }
                }
            }
            .scrollIndicators(.hidden)
            
            FilledButtonView(
                text: "Apply filter",
                onClick: { onEvent(.ShowSheet(shown: false, content: .type))}
            ).frame(height: 48)
        }
        .padding(16)
        
    }
}

#Preview {
    FilterByTypeView(uiState: TransactionsState.companion.default(), onEvent: { _ in })
}
