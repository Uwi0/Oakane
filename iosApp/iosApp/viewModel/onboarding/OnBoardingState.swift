import Foundation
import Shared

struct OnBoardingState {
    var content: OnBoardingContent = .account
    
    init(){}
    
    init(state: OnBoardingStateKt) {
        content = state.onBoardingContent
    }
}
