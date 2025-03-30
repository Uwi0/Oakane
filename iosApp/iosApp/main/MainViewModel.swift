import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class MainViewModel: ObservableObject {
    
    private var viewModel: MainViewModelKt = Koin.shared.get()
    
    func initData() {
        viewModel.doInitData()
    }
}
