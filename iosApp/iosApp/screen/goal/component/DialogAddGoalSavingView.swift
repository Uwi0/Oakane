import SwiftUI
import Shared

struct DialogAddGoalSavingView: View {
    
    let onEvent: (GoalEvent) -> Void
    @State private var amount: Int = 0
    
    var body: some View {
        VStack(alignment: .center) {
            Text("How much do you want to save ?")
                .font(Typography.titleMedium)
            
            Spacer()
                .frame(height: 24)
            
            OutlinedCurrencyTextFieldView(
                label: "Amount",
                value: $amount,
                onValueChange: { amount in onEvent(.Change(amount: amount)) }
            )
            
            Spacer()
                .frame(height: 16)
            
            HStack(spacing: 16) {
                TextButtonView(
                    title: "Cancel",
                    onClick: { onEvent(.Dialog(shown: false, content: .updateAmount))}
                ).frame(width: 120,height: 48)
                FilledButtonView(
                    text: "Save",
                    onClick: { onEvent(.AddSaving())}
                ).frame(width: 120,height: 48)
            }
            .frame(maxWidth: .infinity, alignment: .trailing)
        }
    }
}

#Preview {
    DialogAddGoalSavingView(onEvent: { _ in })
}
