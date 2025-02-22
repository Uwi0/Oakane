import Foundation
import SwiftUI
import Shared

enum WalletSheetContent {
    case wallet
    case icon
}

class WalletSheetState: ObservableObject {
    @Published var content: WalletSheetContent = .wallet
    @Published var walletName: String = ""
    @Published var startBalance: Int = 0
    @Published var selectedColor: Color = .green
    @Published var imageFile: String = ""
    @Published var selectedIcon: CategoryIconName = .wallet
    
    let defaultColors = colorsSelector.map{ color in Color(hex: color.toColorLong()) }
}
