import SwiftUI
import Shared

struct GoalDialogView: View {
    let uiState: GoalState
    let onEvent: (GoalEvent) -> Void
    
    var body: some View {
        PopUpDialog(
            onDismiss: { _ in onEvent(.Dialog(shown: false, content: .updateAmount)) }
        ) {
            switch uiState.dialogContent {
            case .updateAmount: DialogAddGoalSavingView(onEvent: onEvent)
            case .deleteGoal: DeleteGoalDialogView()
            }
        }
    }
    
    @ViewBuilder
    private func DeleteGoalDialogView() -> some View {
        DeleteContentDialogView(
            title: "Are you sure want to delete this Goal ?",
            subtitle: "This action cannot be undone.",
            onDismiss: { onEvent(.Dialog(shown: false, content: .updateAmount)) },
            onConfirm: { onEvent(.DeleteGoal()) }
        )
    }
}

#Preview {
    let defaultValue = GoalState.companion.default()
    GoalDialogView(uiState: defaultValue, onEvent: { _ in })
}
