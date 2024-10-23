import SwiftUI

struct TransactionScreen: View {
    
    @StateObject private var viewModel: TransactionViewModel = TransactionViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    
    init(transactionId: Int64){
        viewModel.initializeData(transactionId: transactionId)
    }
    
    var body: some View {
        ZStack {
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                
            }
        }
        .navigationBarBackButtonHidden(true)
    }
    
}

