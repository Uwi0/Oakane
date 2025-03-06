import SwiftUI
import Shared

struct MoveBalanceDialogView: View {
    
    @Binding var note: String
    @Binding var amount: Int
    let wallets: [WalletModel]
    let onDismiss: () -> Void
    let onConfirm: (WalletModel) -> Void
    
    @State private var selectedOptions: String = ""
    
    private var walletOptions: [String] {
        wallets.map(\.name)
    }
    
    private var selectedWallet: WalletModel {
        wallets.filter{ wallet in wallet.name == selectedOptions}
            .first ?? wallets.first!
    }
    
    init(
        note: Binding<String>,
        amount: Binding<Int>,
        wallets: [WalletModel],
        onDismiss: @escaping () -> Void,
        onConfirm: @escaping (WalletModel) -> Void
    ) {
        self._note = note
        self._amount = amount
        self.wallets = wallets
        self.onDismiss = onDismiss
        self.onConfirm = onConfirm
        self._selectedOptions = State(initialValue: wallets.first?.name ?? "")
    }
    
    var body: some View {
        VStack(spacing: 24) {
            TopContentView()
            BottomContent()
        }
    }
    
    @ViewBuilder
    private func TopContentView() -> some View {
        VStack(spacing: 16) {
            Text("Move Balance").font(Typography.titleLarge)
            WalletsSelections()
            BalanceCurrencyTextField()
            OutlinedTextFieldView(value: $note, placeHolder: "Note")
        }
    }
    
    @ViewBuilder
    private func WalletsSelections() -> some View {
        SelectionPickerView(title: "To Wallet", options: walletOptions, selectedOption: $selectedOptions)
    }
    
    @ViewBuilder
    private func BalanceCurrencyTextField() -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Amount")
            CurrencyTextField(value: $amount, currency: wallets.first?.currency ?? .usd)
        }
    }
    
    @ViewBuilder
    private func BottomContent() -> some View {
        HStack(spacing: 24) {
            TextButtonView(title: "Cancel", onClick: onDismiss)
            FilledContentButtonView(
                onclick: { onConfirm(selectedWallet) },
                content: { Text("Move Balance") }
            )
                .frame(width: 148)
        }
        .frame(maxWidth: .infinity, alignment: .trailing)
    }
}

#Preview {
    MoveBalanceDialogView(
        note: .constant("Hello world"),
        amount: .constant(0),
        wallets: [],
        onDismiss: {},
        onConfirm: { _ in }
    )
}
