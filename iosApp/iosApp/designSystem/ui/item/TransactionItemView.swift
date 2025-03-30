import SwiftUI
import Shared


struct TransactionItemView: View {
    
    let transaction: TransactionModel
    private let imageSize: CGFloat = 48
    private var color: Color {
        transaction.type == .income ? ColorTheme.primary : ColorTheme.error
    }
    
    var body: some View {
        HStack(spacing: 16) {
            Image(ImageConstants.oakaneIcon)
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
                Text("\(transaction.amount.toFormatCurrency(currency: transaction.currency))")
                    .foregroundStyle(color)
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
