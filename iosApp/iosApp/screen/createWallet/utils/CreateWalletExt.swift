import Foundation
import Shared
import SwiftUI

let colors = colorsSelector.map{ color in Color(hex: color.toColorLong()) }

extension CreateWalletState {
    func toColor() -> Color {
        Color(hex: self.selectedColor.toColorLong())
    }
}

extension CreateWalletState {
    func toBalance() -> Int {
        let balance = self.startBalance
        return Int(balance)
    }
}

extension Int {
    func toBalanceChanged() -> Shared.CreateWalletEvent.BalanceChanged {
        let formattedBalance = String(self)
        return .BalanceChanged(balance: formattedBalance)
    }
}
