import SwiftUI

struct TransactionsScreen: View {
    @Environment(\.dismiss) private var dismiss
    var body: some View {
        ZStack{
            ColorTheme.surface.ignoresSafeArea()
            VStack{
                TransactionTopAppBarView(onNavigateBack: { dismiss() })
                Spacer()
            }
        }
        .navigationBarBackButtonHidden(true)
        
        
    }
}

#Preview {
    TransactionsScreen()
}
