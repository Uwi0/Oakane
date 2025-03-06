import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class SplashViewModel: ObservableObject {
    @Published private(set) var obBoardingAlreadyRead: Bool = false
    @Published private(set) var termsAndServiceAlreadyRead: Bool = false
    private var viewModel: SplashViewModelKt = Koin.shared.get()
    private var onboardingReaded: AnyCancellable?
    private var termsAndServiceReaded: AnyCancellable?
    
    func initData() {
        viewModel.doInitViewModel()
        observeTermsAndServiceReaded()
        observeOnboardingReaded()
    }
    
    private func observeOnboardingReaded() {
        let publisher = createPublisher(for: viewModel.isAlreadyReadOnBoardingFlow)
        onboardingReaded = publisher.sink{ completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] isAlreadyRead in
            self?.updateOnboarding(isAlreadyRead: isAlreadyRead)
        }
    }
    
    private func updateOnboarding(isAlreadyRead: KotlinBoolean) {
        DispatchQueue.main.async {
            self.obBoardingAlreadyRead = isAlreadyRead.boolValue
        }
    }
    
    private func observeTermsAndServiceReaded() {
        let publihser = createPublisher(for: viewModel.isAlreadyReadTermAndServiceFlow)
        termsAndServiceReaded = publihser.sink{ completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] isAlReady in
            self?.updateTermsAndService(isAlreadyRead: isAlReady)
        }
    }
    
    private func updateTermsAndService(isAlreadyRead: KotlinBoolean) {
        DispatchQueue.main.async {
            self.termsAndServiceAlreadyRead = isAlreadyRead.boolValue
        }
    }
    
    deinit {
        onboardingReaded?.cancel()
        termsAndServiceReaded?.cancel()
    }
}
