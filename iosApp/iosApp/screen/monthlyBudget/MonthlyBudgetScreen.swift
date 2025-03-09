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
                TopAppbar()
                VStack(alignment: .leading, spacing: 24) {
                    MonthlyBudgetTopContentView(
                        budget: Binding(
                            get: { Int(uiState.realAmount) },
                            set: { amount in viewModel.handle(event: .Changed(amount: String(amount)))}
                        ),
                        onEvent: viewModel.handle(event:)
                    )
                    .padding(.horizontal, 16)
                    MonthlyBudgetBottomContentView(
                        isEditMode: uiState.isEditMode,
                        categoryLimits: uiState.categoryLimits,
                        onEvent: viewModel.handle(event:)
                    )
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.vertical, 24)
                Spacer()
                FilledButtonView(
                    text: "Save Budget",
                    onClick: { viewModel.handle(event: .Save())}
                )
                .frame(height: 48)
                .padding(.horizontal, 16)
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
            
            if uiState.dialogShown {
                PopUpDialog(
                    onDismiss: {_ in viewModel.handle(event: .Dialog(shown: false)) }
                ){
                    CreateCategoryLimitDialogView(
                        categoryLimit: uiState.selectedCategoryLimit,
                        categories: uiState.expenseCategories,
                        onEvent: viewModel.handle(event:)
                    )
                }
            }
        }
        .navigationBarBackButtonHidden(true)
        .onAppear(perform: viewModel.initData)
        .onChange(of: viewModel.uiEffect){
            observe(effect: viewModel.uiEffect)
        }
    }
    
    @ViewBuilder
    private func TopAppbar() -> some View {
        VStack {
            NavigationTopAppbar(
                title: "Monthly Budget",
                onAction: { viewModel.handle(event: .NavigateBack())}
            )
            Divider()
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


#Preview {
    MonthlyBudgetScreen()
}
