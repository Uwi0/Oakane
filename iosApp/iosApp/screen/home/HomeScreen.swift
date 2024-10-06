import SwiftUI

struct HomeScreen: View {
    var body: some View {
        ZStack {
            Oakane.background
            .ignoresSafeArea(.all)
            ScrollView {
                VStack(spacing: 16) {
                    TotalBalanceView()
                    MonthlyBudgetView()
                    Text("RecentTransaction")
                    Text("TransactionItemView")
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
