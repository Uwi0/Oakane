import SwiftUI
import Shared

struct WalletsContentView: View {
    
    let walletItems: [WalletItemModel]
    let onEvent: (WalletsEvent) -> Void
    
    var body: some View {
        ScrollView {
            VStack {
                ForEach(walletItems, id: \.self){ walletItem in
                    WalletItemView(
                        wallet: walletItem,
                        onSelectWallet: { onEvent(.SelectWalletBy(id: walletItem.id))}
                    )
                }
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 16)
        }
        .scrollIndicators(.hidden)
    }
}

