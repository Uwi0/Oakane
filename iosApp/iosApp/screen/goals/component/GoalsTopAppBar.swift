import SwiftUI

struct GoalsTopAppBar: View {
    
    @State var query: String = ""
    
    var body: some View {
        VStack(spacing: 16) {
            NavigationTopAppbar(title: "Goals", navigateBack: {})
            OutlinedSearchTextFieldView(query: $query, placeHolder: "Search goal...")
                .padding(.horizontal, 16)
            Divider()
        }
    }
}

#Preview {
    GoalsTopAppBar()
}
