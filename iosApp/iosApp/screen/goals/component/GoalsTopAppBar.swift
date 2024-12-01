import SwiftUI

struct GoalsTopAppBar: View {
    
    let onSearch: (String) -> Void
    @State var query: String = ""
    
    var body: some View {
        VStack(spacing: 16) {
            NavigationTopAppbar(title: "Goals", navigateBack: {})
            OutlinedSearchTextFieldView(query: $query, placeHolder: "Search goal...")
                .padding(.horizontal, 16)
            Divider()
        }
        .onChange(of: query){ newQuery in
            onSearch(newQuery)
        }
    }
}

#Preview {
    GoalsTopAppBar(onSearch: { _ in })
}
