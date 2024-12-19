import Foundation
import Shared

struct HomeState {
    var transactions: [TransactionModel] = []
    var goals: [GoalModel] = []
    var monthlyOverview: MonthlyBudgetOverViewModel = defaultMonthlyBudgetOverView
    var wallet: WalletModel = defaultWallet
}
