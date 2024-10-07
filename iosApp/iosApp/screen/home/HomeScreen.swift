import SwiftUI
import Shared

struct HomeScreen: View {
    let transactions = TransactionModelKt.dummyValues()
    var body: some View {
        ZStack {
            Oakane.background
            .ignoresSafeArea(.all)
            ScrollView {
                VStack(alignment: .leading,spacing: 16) {
                    TotalBalanceView()
                    MonthlyBudgetView()
                    Text("RecentTransaction")
                        .font(Typography.titleMedium)
                    TransactionsView(transactions: transactions)
                    Text("ShowMoreItemView")
                    Text("GoalHeaderView")
                    Text("GoalItemView")
                    Text("ShowMoreButtonView")
                }
                .padding(.vertical, 24)
                .padding(.horizontal, 16)
            }
        }
        
    }
}

#Preview {
    HomeScreen()
}
