import SwiftUI

struct Typography {

    // Body fonts (Roboto)
    static let bodySmall: Font = .system(size: 14)
    static let bodyMedium: Font = .system(size: 16)
    static let bodyLarge: Font = .system(size: 18)

    // Display fonts (Poppins)
    static let displaySmall: Font = .system(size: 24, weight: .semibold)
    static let displayMedium: Font = .system(size: 30, weight: .semibold)
    static let displayLarge: Font = .system(size: 36, weight: .bold)

    // Title fonts (Roboto)
    static let titleSmall: Font = .system(size: 16, weight: .semibold)
    static let titleMedium: Font = .system(size: 20, weight: .semibold)
    static let titleLarge: Font = .system(size: 24, weight: .bold)
}
