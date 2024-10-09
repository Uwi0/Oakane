import SwiftUI
import Shared

struct HomeContentView: View {
    
    let transactions: [TransactionModel]
    let goals: [GoalModel]
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading,spacing: 16) {
                TotalBalanceView()
                MonthlyBudgetView()
                Text("RecentTransaction")
                    .font(Typography.titleMedium)
                TransactionsView(transactions: transactions)
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
    HomeContentView(transactions: transactions, goals: goals)
}
