import SwiftUI
import Shared

struct GoalsTopAppBar: View {
    
    let onEvent: (GoalsEvent) -> Void
    @State private var query: String = ""
    
    var body: some View {
        VStack(spacing: 16) {
            NavigationTopAppbar(
                title: "Goals",
                navigateBack: { onEvent(.NavigateBack())}
            )
            OutlinedSearchTextFieldView(
                query: $query,
                placeHolder: "Search goal..."
            )
            .padding(.horizontal, 16)
            Divider()
        }
        .onChange(of: query){ newQuery in
            onEvent(.FilterBy(query: newQuery))
        }
    }
}

#Preview {
    GoalsTopAppBar(onEvent: { _ in })
}
