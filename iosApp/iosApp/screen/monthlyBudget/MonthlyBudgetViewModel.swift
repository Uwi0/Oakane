import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class MonthlyBudgetViewModel: ObservableObject {
    @Published var uiState: MonthlyBudgetState = MonthlyBudgetState.companion.default()
    @Published var uiEffect: MonthlyBudgetEffect? = nil
    
    private let viewModel: MonthlyBudgetViewModelKt = Koin.shared.get()
    private var stateCancellable: AnyCancellable?
    private var effectCancellable: AnyCancellable?
    
    init() {
        observeState()
        observeEffect()
    }
    
    private func observeState(){
        let publisher = createPublisher(for: viewModel.uiStateFlow)
        stateCancellable = publisher.sink { completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] state in
            self?.update(state: state)
        }
    }
    
    private func update(state: MonthlyBudgetState){
        DispatchQueue.main.async {
            self.uiState = state
        }
    }
    
    private func observeEffect(){
        let publisher = createPublisher(for: viewModel.effect)
        effectCancellable = publisher.sink{ completion in
            print("completion \(completion)")
        } receiveValue: { [weak self] effect in
            self?.update(effect: effect)
        }
    }
    
    private func update(effect: MonthlyBudgetEffect){
        DispatchQueue.main.async {
            self.uiEffect = effect
        }
    }
    
    func initData(){
        viewModel.initializeData()
    }
    
    func handle(event: MonthlyBudgetEvent){
        viewModel.handleEvent(event: event)
    }
    
    deinit {
        stateCancellable?.cancel()
        effectCancellable?.cancel()
    }
}
