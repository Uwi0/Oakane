import SwiftUI
import Shared

struct TransactionsView: View {
    let transactions: [TransactionModel]
    var body: some View {
        ScrollView(.vertical) {
            VStack(spacing: 16) {
                ForEach(transactions, id: \.id) { transaction in
                    TransactionItemView(transaction: transaction)
                }
            }
            .padding(.vertical, 24)
            .padding(.horizontal, 16)
        }
        .scrollIndicators(.hidden)
    }
}

#Preview {
    let dummyValues = TransactionModelKt.dummyValues()
    TransactionsView(transactions: dummyValues)
}
