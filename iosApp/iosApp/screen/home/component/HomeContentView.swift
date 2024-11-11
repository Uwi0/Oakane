import SwiftUI
import Shared

struct HomeContentView: View {
    
    @Binding var showDrawer: Bool
    
    let transactions: [TransactionModel]
    let goals: [GoalModel]
    let onShowTransactionClick: () -> Void
    
    private var toolbarVisibility: Visibility {
        showDrawer ? .hidden : .visible
    }
    
    var body: some View {
        VStack {
            HStack {
                ZStack {
                    Button(
                        action: { showDrawer.toggle() },
                        label: {
                            Image(systemName: "line.3.horizontal")
                                .tint(ColorTheme.onSurface)
                                .font(.title)
                        }
                    )
                }
                .frame(width: 48, height: 48, alignment: .center)
                Spacer()
                Text("Dashboard")
                    .font(Typography.titleMedium)
                
                Spacer()
                ZStack {
                    
                }
                .frame(width: 48, height: 48)
            }
            .frame(maxWidth: .infinity, alignment: .center)
            ScrollView {
                VStack(alignment: .leading,spacing: 16) {
                    TotalBalanceView()
                    MonthlyBudgetView()
                    Text("RecentTransaction")
                        .font(Typography.titleMedium)
                    HomeTransactionsView(transactions: transactions)
                    if transactions.count == 3 {
                        ShowMoreItemView(onClick: { onShowTransactionClick()})
                    }
                    GoalHeaderView(isVisible: true)
                    GoalsView(goals: goals)
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
    let transactions = TransactionModelKt.dummyValues()
    let goals = GoalModelKt.dummyGoals()
    NavigationView {
        HomeContentView(
            showDrawer: .constant(false),
            transactions: transactions,
            goals: goals,
            onShowTransactionClick: {}
        )
    }
}
