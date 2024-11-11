import SwiftUI
import Shared

struct DrawerButtonView: View {
    
    let menu: DrawerMenuNavigation
    let selectedMenu: DrawerMenuNavigation
    let onClick: (DrawerMenuNavigation) -> Void
    
    private var isSelected: Bool {
        selectedMenu == menu
    }
    
    private var backgroundColor: Color {
        isSelected ? ColorTheme.secondaryContainer : Color.clear
    }
    
    private var textColor: Color {
        isSelected ? ColorTheme.onSecondaryContainer : ColorTheme.onSurfaceVariant
    }
    
    var body: some View {
        Button(
            action: { onClick(menu) },
            label: {
                Text("\(menu.title)")
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .foregroundStyle(textColor)
                    .padding()
                    .background(backgroundColor)
                    .clipShape(RoundedRectangle(cornerRadius: 180))
            }
        )
    }
}

#Preview {
    VStack {
        DrawerButtonView(
            menu: DrawerMenuNavigation.dashboard,
            selectedMenu: DrawerMenuNavigation.dashboard,
            onClick: { _ in }
        )
        DrawerButtonView(
            menu: DrawerMenuNavigation.categories,
            selectedMenu: DrawerMenuNavigation.dashboard,
            onClick: { _ in }
        )
    }
    
}
