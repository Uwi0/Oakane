import SwiftUI

struct MoveBalanceDialogView: View {
    
    @Binding var note: String
    @Binding var amount: Int
    let onDismiss: () -> Void
    let onConfirm: () -> Void
    
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
            BalanceCurrencyTextField()
            OutlinedTextFieldView(value: $note, placeHolder: "Note")
        }
    }
    
    @ViewBuilder
    private func BalanceCurrencyTextField() -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Amount")
            CurrencyTextField(value: $amount, currency: .usd)
        }
    }
    
    
    @ViewBuilder
    private func BottomContent() -> some View {
        HStack(spacing: 24) {
            TextButtonView(title: "Cancel", onClick: onDismiss)
            FilledContentButtonView(content: {Text("Move Balance")}, onclick: onConfirm)
                .frame(width: 148)
        }
        .frame(maxWidth: .infinity, alignment: .trailing)
    }
}

#Preview {
    MoveBalanceDialogView(
        note: .constant("Hello world"),
        amount: .constant(0),
        onDismiss: {},
        onConfirm: {}
    )
}
