import Foundation
import Shared

struct HomeState {
    var transactions: [TransactionModel] = []
    var goals: [GoalModel] = []
    var monthlyOverview: MonthlyBudgetOverViewModel = defaultMonthlyBudgetOverView
    var wallet: WalletModel = defaultWallet
    
    init(){}
    
    init(state: HomeStateKt){
        transactions = state.transactions
        goals = state.goals
        monthlyOverview = state.monthlyBudgetOverView
        wallet = state.wallet
    }
}
