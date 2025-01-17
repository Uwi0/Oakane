import Foundation
import Shared

struct SettingsState {
    var currentCurrency: Currency = .idr
    
    init(){}
    
    init(state: SettingsStateKt){
        self.currentCurrency = state.currency
    }
}
