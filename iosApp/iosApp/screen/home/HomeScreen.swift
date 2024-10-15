import SwiftUI
import Shared

struct HomeScreen: View {
    let goals = GoalModelKt.dummyGoals()
    
    @Binding var isShowingAddTransaction: Bool
    
    @StateObject private var viewModel: HomeViewModelWrapper = HomeViewModelWrapper()
    
    var body: some View {
        GeometryReader { geometryReader in
            ZStack {
                ColorTheme.surface
                .ignoresSafeArea(.all)
                
                HomeContentView(transactions: viewModel.transactions,goals: goals)
                
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
        

    }
}

#Preview {
    HomeScreen(isShowingAddTransaction: .constant(false))
}
