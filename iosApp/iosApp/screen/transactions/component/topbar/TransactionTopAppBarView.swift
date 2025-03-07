import SwiftUI
import Shared

struct TransactionTopAppBarView: View {
    
    let uiState: TransactionsState
    let onEvent: (TransactionsEvent) -> Void
    var showDrawer: Bool
    @State private  var query: String
    
    init(uiState: TransactionsState,showDrawer: Bool = false, onEvent: @escaping (TransactionsEvent) -> Void) {
        self.uiState = uiState
        self.onEvent = onEvent
        self.query = uiState.searchQuery
        self.showDrawer = showDrawer
    }
    
    var body: some View {
        VStack {
            VStack(spacing: 16) {
                TransactionNavBarView(showDrawer: showDrawer, onClick: onClickEvent)
                
                OutlinedSearchTextFieldView(query: $query, placeHolder: "Search Transactions...")
                    .onChange(of: query) {
                        onEvent(.FilterBy(query: query))
                    }
                
                FilterSelectorView(
                    uiState: uiState,
                    onEvent: onEvent
                )
            }
            .padding(16)
            .background(ColorTheme.surface)
            Divider()
        }
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
