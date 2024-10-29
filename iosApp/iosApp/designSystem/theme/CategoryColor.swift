import SwiftUI

struct CategoryColor {
    // INCOME
    static let salary = Color(hex: 0x4CAF50) // Green for stability and growth
    static let freelance = Color(hex: 0x66BB6A) // Lighter green for independence and growth
    static let businessProfits = Color(hex: 0x81C784) // Light green, signaling financial success
    static let rentalIncome = Color(hex: 0xA5D6A7) // Soft green for passive income
    static let interest = Color(hex: 0x388E3C) // Darker green, representing compounded growth
    static let dividends = Color(hex: 0x43A047) // Medium green, associated with growth and investment
    static let gifts = Color(hex: 0xFFD54F) // Golden yellow for generosity and gifting

    // HOUSING
    static let rentMortgage = Color(hex: 0x795548) // Earthy brown, associated with home
    static let utilities = Color(hex: 0x64B5F6) // Light blue, common for water and energy
    static let homeInsurance = Color(hex: 0x2196F3) // Blue, symbolizing security and trust
    static let propertyTaxes = Color(hex: 0x1E88E5) // Darker blue, representing responsibility
    static let homeMaintenance = Color(hex: 0x8D6E63) // Muted brown for repairs and maintenance
    static let hoaFees = Color(hex: 0xA1887F) // Neutral brown-gray, symbolizing community fees

    // TRANSPORTATION
    static let carPayment = Color(hex: 0x757575) // Gray, symbolizing vehicles and machinery
    static let gas = Color(hex: 0xFFA726) // Orange, representing fuel
    static let insurance = Color(hex: 0xBDBDBD) // Light gray for coverage and protection
    static let parkingFees = Color(hex: 0xFFCC80) // Light orange for parking expenses
    static let publicTransportation = Color(hex: 0x90A4AE) // Cool gray-blue for shared transit
    static let maintenance = Color(hex: 0xB0BEC5) // Soft gray for ongoing care

    // FOOD
    static let groceries = Color(hex: 0x8BC34A) // Green, symbolizing freshness
    static let restaurants = Color(hex: 0xFF7043) // Warm orange for dining out
    static let diningOut = Color(hex: 0xFFA726) // Orange, inviting and energetic

    // ENTERTAINMENT
    static let movies = Color(hex: 0x5C6BC0) // Indigo, giving a cinematic feel
    static let concerts = Color(hex: 0xBA68C8) // Purple, representing fun and nightlife
    static let subscriptions = Color(hex: 0x7986CB) // Soft blue-purple for ongoing services
    static let hobbies = Color(hex: 0x9575CD) // Lavender, for creative and personal interests
    static let travel = Color(hex: 0x42A5F5) // Sky blue for exploration and freedom

    // EDUCATION
    static let tuition = Color(hex: 0x283593) // Deep blue for institutional fees
    static let books = Color(hex: 0x673AB7) // Purple, traditionally associated with knowledge
    static let supplies = Color(hex: 0x7986CB) // Muted blue for general educational items

    // HEALTHCARE
    static let doctorsVisits = Color(hex: 0xE57373) // Red, representing health and medical care
    static let prescriptions = Color(hex: 0xF06292) // Pink-red, associated with pharmaceuticals
    static let insurancePremiums = Color(hex: 0xD32F2F) // Deep red, signifying protection
    static let dentalCare = Color(hex: 0xB39DDB) // Lavender for dental care
    static let visionCare = Color(hex: 0x9575CD) // Soft purple, representing vision care

    // PERSONAL CARE
    static let hair = Color(hex: 0xEF9A9A) // Light pink, associated with beauty and grooming
    static let nails = Color(hex: 0xF48FB1) // Pink, symbolizing self-care and pampering
    static let clothing = Color(hex: 0xAB47BC) // Purple, representing style and fashion
    static let toiletries = Color(hex: 0xFFCDD2) // Very light pink for personal items

    // SHOPPING
    static let giftsExpense = Color(hex: 0xFFD54F) // Golden yellow, similar to income gifts
    static let clothes = Color(hex: 0xCE93D8) // Pastel purple for shopping and style
    static let electronics = Color(hex: 0x90CAF9) // Light blue, tech-inspired
    static let householdItems = Color(hex: 0xFFF176) // Soft yellow, for home essentials

    // DEBT
    static let creditCardPayments = Color(hex: 0x607D8B) // Slate blue-gray, symbolizing credit
    static let loanPayments = Color(hex: 0x455A64) // Dark blue-gray, representing obligations
    static let interestExpense = Color(hex: 0x78909C) // Muted blue-gray for interest fees
}

extension Color {
    init(hex: Int) {
        let red = Double((hex >> 16) & 0xFF) / 255.0
        let green = Double((hex >> 8) & 0xFF) / 255.0
        let blue = Double(hex & 0xFF) / 255.0
        self.init(red: red, green: green, blue: blue)
    }
}
