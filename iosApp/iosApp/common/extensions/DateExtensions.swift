import Foundation

extension Date {
    func formmatTo(_ format: String) -> String {
        let formatter = DateFormatter()
        formatter.dateFormat = format
        return formatter.string(from: self)
    }
}

extension Date {
    func toInt64() -> Int64 {
        return Int64(timeIntervalSince1970 * 1000)
    }
}
