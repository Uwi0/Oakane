import SwiftUI
import Shared

struct TransactionTopContentView: View {
    
    let transaction: TransactionModel
    
    private var color: Color {
        transaction.type == .income ? ColorTheme.primary : ColorTheme.error
    }
    
    var body: some View {
        VStack(alignment: .center) {
            Text("Rp: \(transaction.amount.formatted())")
                .font(Typography.displayMedium)
                .foregroundStyle(color)
            Text(transaction.title)
                .font(Typography.labelLarge)
        }
        .frame(maxWidth: .infinity)
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}

#Preview {
    let transactionModel = TransactionModelKt.dummyValue()
    TransactionTopContentView(transaction: transactionModel)
}
