import SwiftUI

struct ReportsScreen: View {
    
    @StateObject private var viewModel: ReportsViewModel = ReportsViewModel()
    
    var uiState: ReportsState { viewModel.uiState }
    
    var body: some View {
        VStack {
            ReportsTopbarView()
            Spacer()
            ScrollView {
                reportsItem()
            }
            .scrollIndicators(.hidden)
        }
        .background(content: { ColorTheme.surface.ignoresSafeArea()})
        .navigationBarBackButtonHidden(true)
    }
    
    @ViewBuilder func reportsItem() -> some View {
        VStack(spacing: 16) {
            ForEach(uiState.reports, id: \.id) { report in
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
