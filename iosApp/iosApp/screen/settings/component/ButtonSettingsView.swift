import SwiftUI

struct ButtonSettingsView: View {
    let title: String
    let iconName: String
    let onClick: () -> Void
    
    var body: some View {
        OutlinedContentButtonView(onClick: onClick) {
            Image(systemName: iconName)
            Text(title)
                .font(Typography.titleMedium)
            Spacer()
        }
    }
}

#Preview {
    ButtonSettingsView(
        title: "Settings",
        iconName:  "gear",
        onClick: { print("Button tapped!") }
    )
}
