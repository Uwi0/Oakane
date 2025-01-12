import Foundation
import Shared

struct ReportsState {
    var reportsKt: [ReportModelKt] = []
    var reports: [ReportModel] = []
    var monthlyOverView: MonthlyBudgetOverView = defaultMonthlyBudgetOverView
    var totalBalance: Double = 0
    var wallets: [WalletItemModel] = []
    var selectedName: String = ""
    var selectedMonth: MonthReport = MonthReportKt.currentMonth()
    
    init(){}
    
    init(state: ReportsStateKt){
        reportsKt = state.reports
        monthlyOverView = state.monthlyOverView
        totalBalance = state.totalBalance
        wallets = state.displayedWallets
        selectedName = state.selectedWalletName
        selectedMonth = state.selectedMonth
        reports = state.reports.enumerated().map{ (index, report) in
            ReportModel(
                id: report.id,
                title: report.name,
                color: report.formattedColor,
                proportion: Double(truncating: state.proportions[index]))
        }
    }
}
