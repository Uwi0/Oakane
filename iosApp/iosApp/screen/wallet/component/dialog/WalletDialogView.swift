import SwiftUI
import Shared

struct WalletDialogView: View {
    
    let uiState: WalletState
    let onEvent: (WalletEvent) -> Void
    @State private var amount: Int = 0
    
    private let title = "Are you sure want to delete this wallet?"
    private let subtitle = "This action can't be undone and will delete all transactions in this wallet"
    private let dismissEvent: WalletEvent = .ShowDialog(content: .moveBalance, shown: false)
    private let confirmDelete: WalletEvent = .ConfirmDelete()
    private let confirmMoveBalance: WalletEvent = .MoveBalance()
    
    var body: some View {
        PopUpDialog(onDismiss: { _ in onEvent(dismissEvent) }) {
            switch uiState.dialogContent {
            case .moveBalance: MoveBalanceDialogContent()
            case .deleteWallet: DeleteWalletDialogContent()
            }
        }
    }
    
    @ViewBuilder
    private func DeleteWalletDialogContent() -> some View {
        DeleteContentDialogView(
            title: title,
            subtitle: subtitle,
            onDismiss: { onEvent(dismissEvent) },
            onConfirm: { onEvent(confirmDelete) }
        )
    }
    
    @ViewBuilder
    private func MoveBalanceDialogContent() -> some View {
        MoveBalanceDialogView(
            note: Binding(
                get: { uiState.moveBalanceNote},
                set: { note in onEvent(.AddNote(note: note))}
            ),
            amount: $amount,
            wallets: uiState.filteredWallet,
            onDismiss: { onEvent(dismissEvent) },
            onConfirm: { wallet in
                onEvent(.AddBalance(balance: String(amount)))
                onEvent(.AddSelectedWalletTo(wallet: wallet))
                onEvent(confirmMoveBalance)
            }
        )
    }
}

#Preview {
    let defaultValue = WalletState.companion.default()
    WalletDialogView(uiState: defaultValue, onEvent: { _ in })
}
