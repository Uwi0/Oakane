import SwiftUI

struct AddTransactionScreen: View {
    
    @Environment(\.dismiss) private var dismiss
    @State private var state = AddTransactionState()
    
    var body: some View {
        ZStack {
            ColorTheme.surface.ignoresSafeArea()
            VStack(spacing: 16) {
                OutlinedTextFieldView(value: $state.title, placeHolder: "Title")
                OutlinedNumericTextFieldView(value: $state.amount, placeHolder: "Amount")
                OutlinedTextFieldView(value: $state.note, placeHolder: "Note")
                Spacer()
                FilledButtonView(text: "Save Transaction", onClick: { dismiss() })
                    .frame(height: 48)
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 24)
            
        }
        .navigationBarBackButtonHidden(true)
        .toolbar {
            ToolbarItem(placement: .topBarLeading) {
                IconButtonView(name: "arrow.left", size: 16, onClick: { dismiss() })
            }
            ToolbarItem(placement: .topBarLeading) {
                Text("Add Transaction")
                    .font(Typography.titleSmall)
            }
        }
        
    }
}

#Preview {
    AddTransactionScreen()
}
