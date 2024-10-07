import SwiftUI
import Shared

struct HomeScreen: View {
    let transactions = TransactionModelKt.dummyValues()
    var body: some View {
        ZStack {
            ColorTheme.background
            .ignoresSafeArea(.all)
            ScrollView {
                VStack(alignment: .leading,spacing: 16) {
                    TotalBalanceView()
                    MonthlyBudgetView()
                    Text("RecentTransaction")
                        .font(Typography.titleMedium)
                    TransactionsView(transactions: transactions)
                    ShowMoreItemView(onClick: {})
                    GoalHeaderView(isVisible: true)
                    Text("GoalItemView")
                    ShowMoreItemView(onClick: {})
                }
                .padding(.vertical, 24)
                .padding(.horizontal, 16)
            }
            .scrollIndicators(.hidden)
        }
        
    }
}

#Preview {
    HomeScreen()
}
