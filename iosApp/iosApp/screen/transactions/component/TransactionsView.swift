import SwiftUI
import Shared

struct TransactionsView: View {
    
    let transactions: [TransactionModel]
    let deleteTransaction: (IndexSet) -> Void
    
    var body: some View {
        ZStack {
            List {
                ForEach(transactions, id: \.id) { transaction in
                    TransactionItemView(transaction: transaction)
                        .listRowBackground(Color.clear)
                        .listRowSeparator(.hidden)
                        .listRowInsets(EdgeInsets(top: 8, leading: 16, bottom: 8, trailing: 16))
                }
                .onDelete(perform: deleteTransaction)
            }
            .listStyle(PlainListStyle())
            .padding(.vertical, 8)
            .scrollIndicators(.hidden)
        }
        
    }
}

#Preview {
    let dummyValues = TransactionModelKt.dummyValues()
    TransactionsView(transactions: dummyValues, deleteTransaction: {_ in})
}
