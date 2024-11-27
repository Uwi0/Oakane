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
}
