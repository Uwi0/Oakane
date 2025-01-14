import SwiftUI
import Shared

struct OnBoardingScreen: View {
    
    @EnvironmentObject private var navigation: AppNavigation
    @StateObject private var viewModel = OnBoardingViewModel()
    @AppStorage(UserDefaultsKeys.onBoardingAlreadyRead) private var onboardingAlreadyRead: Bool = false
    private var content: OnBoardingContent { viewModel.uiState.content }
    
    var body: some View {
        VStack {
            switch content {
            case .account: AccountContentView(onEvent: viewModel.handle(event:))
            case .importBackup: ImportBackupContentView(onEvent: viewModel.handle(event:))
            case .selectCurrency: SelectCurrencyContentView(onEvent: viewModel.handle(event:))
            case .createWallet: CreateWalletContentView(onEVent: viewModel.handle(event:))
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
            case .navigateToHome:
                DispatchQueue.main.async {
                    onboardingAlreadyRead = true
                    navigation.navigate(to: .home)
                }
                
            case .restoreBackup:
                print("Restore backup")
            case .showError:
                print("Show error")
            }
        }
        viewModel.uiEffect = nil
    }
}

#Preview {
    OnBoardingScreen()
}
