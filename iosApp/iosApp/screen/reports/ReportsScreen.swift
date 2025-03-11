import SwiftUI
import Shared

struct ReportsScreen: View {
    
    @Binding var openDrawer: Bool
    var showDrawer: Bool = false
    @EnvironmentObject private var navigation: AppNavigation
    @StateObject private var viewModel: ReportsViewModel = ReportsViewModel()
    @State private var viewController: UIViewController?
    
    var uiState: ReportsState { viewModel.uiState }
    
    var body: some View {
        VStack {
            TopAppbar()
            ScrollView {
                VStack(spacing: 16) {
                    ButtonFilterReportView(
                        wallets: uiState.wallets,
                        onEvent: viewModel.handle(event:)
                    )
                    .padding(.horizontal, 16)
                    .padding(.top, 24)
                    
                    DonutChartComponentView(
                        reports: uiState.reports,
                        walletBalance: uiState.totalBalance,
                        currency: uiState.currency
                    )
                    .padding(.horizontal, 16)
                        
                    ReportBudgetContentView(item: uiState.monthlyOverView)
                        .padding(.horizontal, 16)
                    
                    ReportsItem()
                }
            }
            .scrollIndicators(.hidden)
        }
        .background(content: { ColorTheme.surface.ignoresSafeArea()})
        .background(ViewControllerAccessor { vc in viewController = vc} )
        .navigationBarBackButtonHidden(true)
        .onChange(of: viewModel.uiEffect) { observe(effect: viewModel.uiEffect) }
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
                showDrawer: showDrawer,
                actionContent: {
                    Image(systemName: "square.and.arrow.down")
                        .frame(width: 24, height: 24)
                        .onTapGesture { viewModel.handle(event: .GenerateReport())}
                },
                onAction: onAction
            )
            Divider()
        }
    }
    
    private func onAction() {
        if showDrawer {
            viewModel.handle(event: .OpenDrawer())
        } else {
            viewModel.handle(event: .NavigateBack())
        }
    }
    
    private func observe(effect: ReportsEffect?){
        guard let effect else { return }
        
        switch onEnum(of: effect) {
        case .generateReport(let effect):generateReports(values: effect.reports)
        case .navigateBack : navigation.navigateBack()
        case .showError(let effect): print(effect.message)
        case .openDrawer: openDrawer = !openDrawer
            
        }
        viewModel.uiEffect = nil
    }
    
    private func generateReports(values: [ReportCsvModelKt]) {
        let reports = values.toReportCsvModels()
        let reportsCsv = generateCSV(from: reports)
        let dateFormat = "dd-mm-yyyy-HH-mm-ss"
        let formattedDate = DateUtilsKt.getCurrentDateWith(format: dateFormat)
        if let controller = viewController {
            saveDocument(
                value: reportsCsv,
                fileName: "reports-\(formattedDate).csv",
                viewController: controller
            )
        }
    }
    
}

#Preview {
    ReportsScreen(openDrawer: .constant(false))
}
