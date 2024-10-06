import SwiftUI

struct HomeScreen: View {
    var body: some View {
        ScrollView {
            VStack(spacing: 16) {
                Text("TotalBalanceView")
                Text("MonthlyBudgetView")
                Text("RecentTransaction")
                Text("TransactionItemView")
                Text("ShowMoreItemView")
                Text("GoalHeaderView")
                Text("GoalItemView")
                Text("ShowMoreButtonView")
            }
        }
    }
}

#Preview {
    HomeScreen()
}
