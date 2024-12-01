import SwiftUI
import Shared

struct DialogDeleteGoalConfirmationView: View {
    
    let onEvent: (GoalEvent) -> Void
    
    var body: some View {
        VStack(alignment: .leading) {
            Text("Are you sure want to delete this Goal ?")
                .font(Typography.headlineSmall)
            Spacer()
                .frame(height: 16)
            Text("This action cannot be undone.")
                .font(Typography.bodyMedium)
            Spacer()
                .frame(height: 24)
            HStack(spacing: 16) {
                Button(action: { onEvent(.Dialog(shown: false, content: .updateAmount))}) {
                    Text("Cancel")
                        .font(Typography.labelLarge)
                        .foregroundStyle(ColorTheme.primary)
                }
                .buttonStyle(PlainButtonStyle())
                .frame(width: 120,height: 48)
                
                FilledButtonView(
                    text: "Delete",
                    bgColor: ColorTheme.error,
                    onClick: { onEvent(.DeleteGoal()) }
                )
                    .frame(width: 120,height: 48)
            }
            .frame(maxWidth: .infinity, alignment: .trailing)
        }
    }
}

#Preview {
    DialogDeleteGoalConfirmationView(onEvent: { _ in })
}
