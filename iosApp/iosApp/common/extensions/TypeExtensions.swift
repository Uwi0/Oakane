import Foundation

extension Double {
    func toIDRCurrency() -> String {
        let formatter = NumberFormatter()
        formatter.locale = Locale(identifier: "id_ID") // Set locale for Indonesian
        formatter.numberStyle = .currency
        formatter.currencySymbol = "Rp" // Customize the currency symbol if needed
        formatter.maximumFractionDigits = 0 // For whole numbers without decimals

        return formatter.string(from: NSNumber(value: self)) ?? "Rp0"
    }
}

extension Float {
    func asPercentageString(maximumFractionDigits: Int = 0) -> String {
        let formatter = NumberFormatter()
        formatter.numberStyle = .percent
        formatter.maximumFractionDigits = maximumFractionDigits
        
        return formatter.string(from: NSNumber(value: self)) ?? ""
    }
}
