import SwiftUI
import Shared

struct HomeContentView: View {
    
    let uiState: HomeState
    let onEvent: (HomeEvent) -> Void
    
    
    var body: some View {
        VStack {

            ScrollView {
                VStack(alignment: .leading,spacing: 16) {
                    TotalBalanceView()
                    MonthlyBudgetView()
                    Text("RecentTransaction")
                        .font(Typography.titleMedium)
                    HomeTransactionsView(transactions: uiState.transactions)
                    if uiState.transactions.count > 3 {
                        ShowMoreItemView(onClick: { onEvent(.ToTransactions())})
                    }
                    GoalHeaderView(isVisible: true, onClick: { onEvent(.ToCreateGoal()) })
                    GoalsView(goals: uiState.goals)
                    ShowMoreItemView(onClick: {})
                }
                .padding(.vertical, 24)
                .padding(.horizontal, 16)
                .ignoresSafeArea(.all)
            }
            .scrollIndicators(.hidden)
        }

        
    }
}

#Preview {
    NavigationView {
        HomeContentView(
            uiState: HomeState(),
            onEvent: {_ in }
        )
    }
}
