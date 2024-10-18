import SwiftUI

struct TransactionsScreen: View {
    @Environment(\.dismiss) private var dismiss
    @State var query: String = ""
    var body: some View {
        ZStack{
            ColorTheme.surface.ignoresSafeArea()
            VStack{
                TransactionTopAppBarView(query: $query, onNavigateBack: { dismiss() })
                Spacer()
                Text("query is :\(query)")
                Spacer()
            }
        }
        .navigationBarBackButtonHidden(true)
        
        
    }
}

#Preview {
    TransactionsScreen()
}
