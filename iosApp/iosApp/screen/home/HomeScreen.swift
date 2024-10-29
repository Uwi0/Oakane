import SwiftUI
import Shared

struct HomeScreen: View {
    let goals = GoalModelKt.dummyGoals()
    
    @StateObject private var viewModel: HomeViewModel = HomeViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    var body: some View {
        GeometryReader { geometryReader in
            ZStack {
                ColorTheme.surface
                .ignoresSafeArea(.all)
                
                HomeContentView(
                    transactions: viewModel.transactions,
                    goals: goals,
                    onShowTransactionClick: {
                        navigation.navigate(to: .transactions)
                    }
                )
                
                FabButtonView(
                    size: 56,
                    xPos: geometryReader.size.width - FabConstant.xOffset,
                    yPos: geometryReader.size.height - FabConstant.yOffset,
                    onClick: {
                        navigation.navigate(to: .addTransaction(transactionId: 0))
                    }
                )
            }
        }
        .onAppear {
            viewModel.initViewModel()
        }

    }
}
