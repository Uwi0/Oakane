import Foundation
import Shared

extension FilterWalletLogByDateModel {
    func asTitle() -> String {
        switch onEnum(of: self) {
        case .all: return "All"
        case .custom: return "Custom"
        case .pastMonth: return "Past month"
        case .pastWeek: return "Past week"
        }
    }
}
