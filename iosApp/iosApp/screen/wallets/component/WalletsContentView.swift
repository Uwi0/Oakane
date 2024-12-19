import SwiftUI
import Shared

struct WalletsContentView: View {
    
    let walletItems: [WalletItemModel]
    
    var body: some View {
        ScrollView {
            VStack {
                ForEach(walletItems, id: \.self){ walletItem in
                    WalletItemView(wallet: walletItem)
                }
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 16)
        }
        .scrollIndicators(.hidden)
    }
}

