import SwiftUI
import Shared

struct MonthlyBudgetScreen: View {
    
    @EnvironmentObject private var navigation: AppNavigation
    @StateObject private var viewModel: MonthlyBudgetViewModel = MonthlyBudgetViewModel()
    
    private var uiState: MonthlyBudgetState {
        viewModel.uiState
    }
    
    var body: some View {
        ZStack {
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                MonthlyBudgetTopbarView(
                    title: "Monthly Budget",
                    onNavigateBack: { viewModel.handle(event: .NavigateBack())}
                )
                VStack(alignment: .leading, spacing: 24) {
                    MonthlyBudgetTopContentView()
                    MonthlyBudgetBottomContentView(onEvent: { _ in })
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.vertical, 24)
                .padding(.horizontal, 16)
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        }
        .navigationBarBackButtonHidden(true)
        .onAppear(perform: viewModel.initData)
        .onChange(of: viewModel.uiEffect){
            observe(effect: viewModel.uiEffect)
        }
    }
    
    private func observe(effect: MonthlyBudgetEffect?){
        if let safeEffect = effect {
            switch onEnum(of:safeEffect) {
            case .navigateBack:
                navigation.navigateBack()
            case .showError(let effect):
                print(effect.message)
            }
        }
        viewModel.uiEffect = nil
    }
}

private struct MonthlyBudgetTopbarView: View {
    
    let title: String
    let onNavigateBack: () -> Void
    
    var body: some View {
        VStack {
            NavigationTopAppbar(title: title, navigateBack: onNavigateBack)
            HorizontalDivider(width: 1)
        }
    }
}


#Preview {
    MonthlyBudgetScreen()
}
