import SwiftUI
import Shared

struct TransactionsView: View {
    
    let transactions: [TransactionModel]
    let onEvent: (TransactionsEvent) -> Void
    
    var body: some View {
        ZStack {
            List {
                ForEach(transactions, id: \.id) { transaction in
                    TransactionItemView(transaction: transaction)
                        .listRowBackground(Color.clear)
                        .listRowSeparator(.hidden)
                        .listRowInsets(EdgeInsets(top: 8, leading: 16, bottom: 8, trailing: 16))
                        .onTapGesture {
                            onEvent(.ToDetail(id: transaction.id))
                        }
                }
                .onDelete(perform: deleteTransaction)
            }
            .listStyle(PlainListStyle())
            .padding(.vertical, 8)
            .scrollIndicators(.hidden)
        }
        
    }
    
    private func deleteTransaction(_ indexSet: IndexSet) {
        indexSet.forEach { index in
            let transaction = transactions[index]
            onEvent(.Delete(transaction: transaction))
        }
    }
}

#Preview {
    let dummyValues = TransactionModelKt.dummyValues()
    TransactionsView(
        transactions: dummyValues,
        onEvent: { _ in }
    )
}
