import SwiftUI

struct ColorTheme {
    static let primary = Color("primary")
    static let onPrimary = Color("onPrimary")
    static let primaryContainer = Color("primaryContainer")
    static let onPrimaryContainer = Color("onPrimaryContainer")
    static let secondary = Color("secondary")
    static let onSecondary = Color("onSecondary")
    static let secondaryContainer = Color("secondaryContainer")
    static let onSecondaryContainer = Color("onSecondaryContainer")
    static let tertiary = Color("tertiary")
    static let onTertiary = Color("onTertiary")
    static let tertiaryContainer = Color("tertiaryContainer")
    static let onTertiaryContainer = Color("onTertiaryContainer")
    static let error = Color("error")
    static let onError = Color("onError")
    static let errorContainer = Color("errorContainer")
    static let onErrorContainer = Color("onErrorContainer")
    static let background = Color("background")
    static let onBackground = Color("onBackground")
    static let surface = Color("surface")
    static let onSurface = Color("onSurface")
    static let surfaceVariant = Color("surfaceVariant")
    static let onSurfaceVariant = Color("onSurfaceVariant")
    static let outline = Color("outline")
    static let outlineVariant = Color("outlineVariant")
    static let scrim = Color("scrim")
    static let inverseSurface = Color("inverseSurface")
    static let inverseOnSurface = Color("inverseOnSurface")
    static let inversePrimary = Color("inversePrimary")
    static let surfaceDim = Color("surfaceDim")
    static let surfaceBright = Color("surfaceBright")
    static let surfaceContainerLowest = Color("surfaceContainerLowest")
    static let surfaceContainerLow = Color("surfaceContainerLow")
    static let surfaceContainer = Color("surfaceContainer")
    static let surfaceContainerHigh = Color("surfaceContainerHigh")
    static let surfaceContainerHighest = Color("surfaceContainerHighest")
}

extension Color {
    init(hex: Int32) {
        let red = Double((hex >> 16) & 0xFF) / 255.0
        let green = Double((hex >> 8) & 0xFF) / 255.0
        let blue = Double(hex & 0xFF) / 255.0
        self.init(red: red, green: green, blue: blue)
    }
}

extension Color {
    init(hex: Int64) {
        let red = CGFloat((hex >> 16) & 0xFF) / 255.0
        let green = CGFloat((hex >> 8) & 0xFF) / 255.0
        let blue = CGFloat(hex & 0xFF) / 255.0
        
        self.init(red: red, green: green, blue: blue)
    }
}

extension Color {
    func toHexString() -> String {
        let uiColor = UIColor(self)
        
        var red: CGFloat = 0
        var green: CGFloat = 0
        var blue: CGFloat = 0
        var alpha: CGFloat = 0
        
        guard uiColor.getRed(&red, green: &green, blue: &blue, alpha: &alpha) else {
            return "0xFF4CAF50"
        }
        
        let rgb: Int = (Int)(red * 255) << 16 | (Int)(green * 255) << 8 | (Int)(blue * 255)
        return String(format: "0x%06X", rgb)
    }
}
