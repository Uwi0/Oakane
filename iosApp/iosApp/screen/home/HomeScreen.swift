import SwiftUI
import Shared

struct HomeScreen: View {
    let goals = GoalModelKt.dummyGoals()
    
    @Binding var isShowingAddTransaction: Bool
    @Binding var isShowingTransactions: Bool
    
    @StateObject private var viewModel: HomeViewModel = HomeViewModel()
    
    var body: some View {
        GeometryReader { geometryReader in
            ZStack {
                ColorTheme.surface
                .ignoresSafeArea(.all)
                
                HomeContentView(
                    transactions: viewModel.transactions,
                    goals: goals,
                    onShowTransactionClick: {
                        isShowingTransactions.toggle()
                    }
                )
                
                FabButtonView(
                    size: 56,
                    xPos: geometryReader.size.width - 50,
                    yPos: geometryReader.size.height - 50,
                    onClick: {
                        isShowingAddTransaction.toggle()
                    }
                )
            }
        }
        .onAppear {
            viewModel.initViewModel()
        }

    }
}

#Preview {
    HomeScreen(
        isShowingAddTransaction: .constant(false),
        isShowingTransactions: .constant(false)
    )
}
