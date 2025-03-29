import SwiftUI

struct ButtonNavigationSettingsView: View {
    let icon: String
    let title: String
    let onClick: () -> Void
    var body: some View {
        OutlinedContentButtonView(onClick: onClick){
            Image(systemName: icon)
            Text(title)
                .font(Typography.titleMedium)
            Spacer()
            Image(systemName: "chevron.forward")
        }
    }
}

#Preview {
    ButtonNavigationSettingsView(
        icon: "bell", title: "Reminder", onClick: {}
    )
}
