import SwiftUI

struct ReportsScreen: View {
    
    @StateObject private var viewModel: ReportsViewModel = ReportsViewModel()
    
    var uiState: ReportsState { viewModel.uiState }
    
    var body: some View {
        VStack {
            ReportsTopbarView()
            ScrollView {
                VStack(spacing: 16) {
                    ButtonFilterReportView(wallets: uiState.wallets)
                        .padding(.horizontal, 16)
                        .padding(.top, 24)
                    
                    DonutChartComponentView(reports: uiState.reports, walletBalance: uiState.totalBalance)
                        .padding(.horizontal, 16)
                        
                    ReportBudgetContentView(item: uiState.monthlyOverView)
                        .padding(.horizontal, 16)
                    
                    reportsItem()
                }
            }
            .scrollIndicators(.hidden)
        }
        .background(content: { ColorTheme.surface.ignoresSafeArea()})
        .navigationBarBackButtonHidden(true)
    }
    
    @ViewBuilder func reportsItem() -> some View {
        VStack(spacing: 16) {
            ForEach(uiState.reportsKt, id: \.id) { report in
                ReportItemView(item: report)
            }
            .padding(.horizontal, 16)
        }
    }
    
}

fileprivate struct ReportsTopbarView: View {
    
    var body: some View {
        VStack {
            NavigationTopAppbar(title: "Reports", navigateBack: {})
            Divider()
        }
    }
}

#Preview {
    ReportsScreen()
}
