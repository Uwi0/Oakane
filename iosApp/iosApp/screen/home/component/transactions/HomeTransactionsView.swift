import SwiftUI
import Shared

struct HomeTransactionsView: View {
    let transactions: [TransactionModel]
    private var transactionItem: [TransactionModel] {
        TransactionModelKt.swiftTake(transactions, n: 3)
    }
    var body: some View {
        VStack(spacing: 8) {
            ForEach(transactionItem, id: \.id) { transaction in
                TransactionItemView(transaction: transaction)
            }
        }
    }
}

#Preview {
    let dummyValues = TransactionModelKt.dummyValues()
    HomeTransactionsView(transactions: dummyValues)
}
