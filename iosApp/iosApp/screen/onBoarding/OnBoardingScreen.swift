import SwiftUI
import Shared

struct OnBoardingScreen: View {
    
    @EnvironmentObject private var navigation: AppNavigation
    @StateObject private var viewModel = OnBoardingViewModel()
    @AppStorage(UserDefaultsKeys.onBoardingAlreadyRead) private var onboardingAlreadyRead: Bool = false
    
    private var content: OnBoardingContent {
        viewModel.uiState.onBoardingContent
    }
    
    var body: some View {
        VStack {
            switch content {
            case .account: AccountContentView(onEvent: viewModel.handle(event:))
            case .importBackup: ImportBackupContentView(onEvent: viewModel.handle(event:))
            case .selectCurrency: SelectCurrencyContentView(
                onConfirm: { selectedCurrency in
                    viewModel.handle(event: .OnConfirmCurrency(currency: selectedCurrency))
                }
            )
            case .createWallet: CreateWalletContentView(onEvent: viewModel.handle(event:))
            }
        }
        .navigationBarBackButtonHidden(true)
        .onChange(of: viewModel.uiEffect) { observe(effect:viewModel.uiEffect) }
        .onAppear {
            viewModel.initData()
        }
    }
    
    private func observe(effect: OnBoardingEffect?) {
        if let safeEffect = effect {
            switch onEnum(of: safeEffect) {
            case .navigateToHome: navigateToHome()
            case .restoreBackup: print("Restore backup")
            case .showError(let error): print("error \(error)")
            }
        }
        viewModel.uiEffect = nil
    }
    
    private func navigateToHome() {
        DispatchQueue.main.async {
            onboardingAlreadyRead = true
            navigation.navigate(to: .home)
        }
    }
}

#Preview {
    OnBoardingScreen()
}
