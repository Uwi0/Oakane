import Foundation
import Shared

extension ReminderState {
    func toDate() -> Date {
        var calendar = Calendar.current
        calendar.timeZone = .current
        var component = calendar.dateComponents([.year, .month, .day], from: Date())
        component.hour = Int(selectedHour)
        component.minute = Int(selectedMinute)
        component.second = 0
        component.nanosecond = 0
        
        return calendar.date(from: component) ?? Date()
    }
}

extension Date {
    func toTimeSelected() -> ReminderEvent.TimeSelected {
        let calendar = Calendar.current
        let component = calendar.dateComponents([.hour, .minute], from: self)
        return ReminderEvent.TimeSelected(
            hour: Int32(component.hour ?? 0),
            minute: Int32(component.minute ?? 0)
        )
    }
}
