import SwiftUI

struct ButtonSettingsView: View {
    let title: String
    let iconName: String
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            HStack(spacing: 16) {
                Image(systemName: iconName)
                    .foregroundColor(ColorTheme.primary)
                Text(title)
                    .font(Typography.titleMedium)
                    .foregroundColor(ColorTheme.primary)
            }
            .padding(16)
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(ColorTheme.surface)
            .cornerRadius(10)
            .overlay(
                RoundedRectangle(cornerRadius: 10)
                    .stroke(ColorTheme.primary, lineWidth: 2)
            )
        }
        .buttonStyle(PlainButtonStyle())
    }
}

#Preview {
    ButtonSettingsView(
        title: "Settings",
        iconName:  "gear",
        onClick: { print("Button tapped!") }
    )
}
