import Foundation

extension Date {
    func formmatTo(_ format: String) -> String {
        let formatter = DateFormatter()
        formatter.dateFormat = format
        return formatter.string(from: self)
    }
}

extension Int64 {
    
    func toDateWith(format: String) -> String {
        let date = Date(timeIntervalSince1970: TimeInterval(self) / 1000)
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = format
        dateFormatter.locale = Locale.current
        let formattedDate = dateFormatter.string(from: date)
        return formattedDate
    }
    
}
