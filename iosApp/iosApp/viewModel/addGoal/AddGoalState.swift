import Foundation
import Shared

struct AddGoalState {
    var fileName: String = ""
    var goalName: String = ""
    var targetAmount: Int = 0
    var note: String = ""
    var startDate: Int64 = 0
    var endDate: Int64 = 0
    
    init(){}
    
    init(state: AddGoalStateKt){
        fileName = state.fileName
        goalName = state.goalName
        targetAmount = Int(state.amount)
        note = state.note
        startDate = state.startDate
        endDate = state.endDate
    }
}
