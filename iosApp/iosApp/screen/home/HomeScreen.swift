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
                    xPos: geometryReader.size.width - 50,
                    yPos: geometryReader.size.height - 50,
                    onClick: {
                        navigation.navigate(to: .addTransaction)
                    }
                )
            }
        }
        .onAppear {
            viewModel.initViewModel()
        }

    }
}
