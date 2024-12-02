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
                Text(transaction.category.name)
            }.minimumScaleFactor(1)
            Spacer()
            VStack(alignment: .trailing, spacing: 8) {
                Text("Rp. \(transaction.amount.formatted())")
                    .foregroundStyle(ColorTheme.error)
                    .font(Typography.titleSmall)
                Text("\(transaction.dateCreated.formatDateWith(pattern: "dd MMM yyyy"))")
            }.minimumScaleFactor(1)
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}

#Preview {
    let transaction = TransactionModelKt.dummyValue()
    TransactionItemView(transaction: transaction)
}
