import SwiftUI

struct ReportsScreen: View {
    
    @EnvironmentObject private var navigation: AppNavigation
    @StateObject private var viewModel: ReportsViewModel = ReportsViewModel()
    
    var uiState: ReportsState { viewModel.uiState }
    
    var body: some View {
        VStack {
            TopAppbar()
            ScrollView {
                VStack(spacing: 16) {
                    ButtonFilterReportView(wallets: uiState.wallets, onEvent: viewModel.handle(event:))
                        .padding(.horizontal, 16)
                        .padding(.top, 24)
                    
                    DonutChartComponentView(reports: uiState.reports, walletBalance: uiState.totalBalance)
                        .padding(.horizontal, 16)
                        
                    ReportBudgetContentView(item: uiState.monthlyOverView)
                        .padding(.horizontal, 16)
                    
                    ReportsItem()
                }
            }
            .scrollIndicators(.hidden)
        }
        .background(content: { ColorTheme.surface.ignoresSafeArea()})
        .navigationBarBackButtonHidden(true)
    }
    
    @ViewBuilder private func ReportsItem() -> some View {
        VStack(spacing: 16) {
            ForEach(uiState.reportsKt, id: \.id) { report in
                ReportItemView(item: report)
            }
            .padding(.horizontal, 16)
        }
    }
    
    @ViewBuilder private func TopAppbar() -> some View {
        VStack {
            NavigationTopAppbar(
                title: "Reports",
                actionContent: {
                    Image(systemName: "square.and.arrow.down")
                        .frame(width: 24, height: 24)
                        .onTapGesture {}
                },
                navigateBack: { navigation.navigateBack() }
            )
            Divider()
        }
    }
    
}

#Preview {
    ReportsScreen()
}
