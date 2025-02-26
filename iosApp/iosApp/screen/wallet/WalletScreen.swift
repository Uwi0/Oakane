import SwiftUI
import Shared

struct WalletScreen: View {
    var body: some View {
        VStack {
            WalletTopbar()
            WalletContent()
            Spacer()
        }
        .background(ColorTheme.surface.ignoresSafeArea())
    }
    
    @ViewBuilder
    private func WalletTopbar() -> some View {
        VStack {
            NavigationTopAppbar(
                title: "Wallet",
                actionContent: {
                    BarAction(systemName: "pencil")
                    Spacer().frame(width: 16)
                    BarAction(systemName: "trash")
                },
                navigateBack: {}
            )
            Divider()
        }
    }
    
    @ViewBuilder
    private func BarAction(systemName: String) -> some View {
        Image(systemName: systemName)
            .resizable()
            .frame(width: 24, height: 24)
    }
    
    @ViewBuilder
    private func WalletContent() -> some View {
        VStack(spacing: 16) {
            WalletDetailItemView(state: WalletState.companion.default())
            CardNoteView(note: "Hello world!")
        }
        .padding(.horizontal, 16)
        .padding(.top, 24)
    }
}

#Preview {
    WalletScreen()
}
