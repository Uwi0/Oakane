import Foundation
import Shared

struct GoalState {
    var fileName: String = ""
    var title: String = ""
    var savedAmount: String = ""
    var targetAmount: String = ""
    var progress: Float = 0.0
    var startDate: Int64 = 0
    var endDate: Int64 = 0
    var daysLeft: Int64 = 0
    var note: String = ""
    var isDialogShown: Bool = false
    var dialogContent: GoalDialogContent = .updateAmount
    
    init(){}
    
    init(state: GoalStateKt){
        fileName = state.goal.fileName
        title = state.goal.goalName
        savedAmount = state.savedAmount
        targetAmount = state.targetAmount
        progress = state.goal.progress
        startDate = state.goal.startDate
        endDate = state.goal.endDate
        daysLeft = state.dayLeft
        note = state.goal.note
        isDialogShown = state.dialogShown
        dialogContent = state.dialogContent
    }
}
