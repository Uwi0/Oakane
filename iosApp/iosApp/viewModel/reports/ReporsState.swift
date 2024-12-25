import Foundation
import Shared

struct ReportsState {
    var reports: [ReportModel] = []
    var monthlyOverView: MonthlyBudgetOverViewModel = defaultMonthlyBudgetOverView
    var totalBalance: Double = 0
    var wallets: [WalletItemModel] = []
    var selectedName: String = ""
    var selectedMonth: MonthReport = MonthReportKt.currentMonth()
    
    init(){}
    
    init(state: ReportsStateKt){
        reports = state.reports
        monthlyOverView = state.monthlyOverView
        totalBalance = state.totalBalance
        wallets = state.wallets
        selectedName = state.selectedWalletName
        selectedMonth = state.selectedMonth
    }
}
