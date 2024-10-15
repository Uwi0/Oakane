import SwiftUI
import Shared

struct HomeContentView: View {
    
    @StateObject private var viewModel: HomeViewModelWrapper = HomeViewModelWrapper()
    let goals: [GoalModel]
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading,spacing: 16) {
                TotalBalanceView()
                MonthlyBudgetView()
                Text("RecentTransaction")
                    .font(Typography.titleMedium)
                TransactionsView(transactions: viewModel.transactions)
                ShowMoreItemView(onClick: {})
                GoalHeaderView(isVisible: true)
                GoalsView(goals: goals)
                ShowMoreItemView(onClick: {})
            }
            .padding(.vertical, 24)
            .padding(.horizontal, 16)
        }
        .scrollIndicators(.hidden)
    }
}

#Preview {
    let transactions = TransactionModelKt.dummyValues()
    let goals = GoalModelKt.dummyGoals()
    HomeContentView(goals: goals)
}
