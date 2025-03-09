import SwiftUI
import Shared

struct TransactionTopAppBarView: View {
    
    let uiState: TransactionsState
    var showDrawer: Bool = false
    let onEvent: (TransactionsEvent) -> Void
    
    var body: some View {
        VStack {
            NavigationTopAppbar(
                title: "Transactions",
                showDrawer: showDrawer,
                onAction: onClickEvent
            )
            FilterComponent()
            Divider()
        }
    }
    
    @ViewBuilder
    private func FilterComponent() -> some View {
        VStack(spacing: 16) {
            OutlinedSearchTextFieldView(
                query: Binding(
                    get: { uiState.searchQuery },
                    set: {query in onEvent(.FilterBy(query: query))}
                ),
                placeHolder: "Search Transactions..."
            )
            
            FilterSelectorView(
                uiState: uiState,
                onEvent: onEvent
            )
        }
        .padding(.horizontal, 16)
        .background(ColorTheme.surface)
    }
    
    private func onClickEvent() {
        if showDrawer {
            onEvent(.OpenDrawer())
        } else {
            onEvent(.NavigateBack())
        }
    }
}

#Preview {
    TransactionTopAppBarView(
        uiState: TransactionsState.companion.default(),
        onEvent: {_ in }
    )
}
