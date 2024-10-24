import SwiftUI
import Shared

struct TransactionTopContentView: View {
    
    let transaction: TransactionModel
    
    var body: some View {
        HStack {
            Image(ImageConstants.fubukiStare)
                .resizable()
                .frame(width: 48, height: 48)
                .clipShape(Circle())
                .scaledToFit()
            Spacer()
            VStack(alignment: .trailing) {
                Text(transaction.title)
                    .font(Typography.titleSmall)
                Text("Rp: \(transaction.amount.formatted())")
                    .font(Typography.titleMedium)
            }
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}

#Preview {
    let transactionModel = TransactionModelKt.dummyValue()
    TransactionTopContentView(transaction: transactionModel)
}
