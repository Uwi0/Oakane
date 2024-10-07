import SwiftUI
import Shared


struct TransactionItemView: View {
    
    let transaction: TransactionModel
    private let imageSize: CGFloat = 48
    
    var body: some View {
        HStack(spacing: 16) {
            Image(ImageConstants.fubukiStare)
                .resizable()
                .frame(width: imageSize, height: imageSize)
                .clipShape(Circle())
            VStack(alignment: .leading, spacing: 8) {
                Text(StringExtKt.asTextEllipsis(transaction.title, maxLine: 8))
                    .font(Typography.titleSmall)
                Text(transaction.tag)
            }.minimumScaleFactor(1)
            Spacer()
            VStack(alignment: .trailing, spacing: 8) {
                Text("Rp. \(transaction.amount)")
                    .foregroundStyle(Oakane.error)
                    .font(Typography.titleSmall)
                Text(transaction.date)
            }.minimumScaleFactor(1)
        }
        .customBackground(backgroundColor: Oakane.surface)
    }
}

#Preview {
    let transaction = TransactionModelKt.dummyValue()
    TransactionItemView(transaction: transaction)
}
