import SwiftUI
import Shared

struct WalletDialogView: View {
    
    let uiState: WalletState
    let onEvent: (WalletEvent) -> Void
    
    private let title = "Are you sure want to delete this wallet?"
    private let subtitle = "This action can't be undone and will delete all transactions in this wallet"
    private let dismissEvent: WalletEvent = .ShowDialog(content: .moveBalance, shown: false)
    private let confirmEvent: WalletEvent = .ConfirmDelete()
    
    var body: some View {
        PopUpDialog(onDismiss: { _ in onEvent(dismissEvent) }) {
            DeleteContentDialogView(
                title: title,
                subtitle: subtitle,
                onDismiss: { onEvent(dismissEvent)},
                onConfirm: { onEvent(confirmEvent) }
            )
        }
    }
}

#Preview {
    let defaultValue = WalletState.companion.default()
    WalletDialogView(uiState: defaultValue, onEvent: { _ in })
}
