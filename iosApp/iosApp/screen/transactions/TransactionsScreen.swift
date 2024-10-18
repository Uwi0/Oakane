import SwiftUI

struct TransactionsScreen: View {
    var body: some View {
        ZStack{
            ColorTheme.surface.ignoresSafeArea()
            VStack{
                Text("Transaction Screen")
            }
        }
        .navigationBarBackButtonHidden(true)
        
    }
}

#Preview {
    TransactionsScreen()
}
