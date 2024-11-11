import SwiftUI
import Shared

struct DrawerMenuView: View {
    
    @Binding var isShowing: Bool
    let onMenuClick: (DrawerMenuNavigation) -> Void
    
    @State private var selectedButton = DrawerMenuNavigation.dashboard
    
    private let drawerWidth = 0.85
    
    var body: some View {
        GeometryReader { proxy in
            ZStack {
                if isShowing {
                    Rectangle()
                        .opacity(0.3)
                        .ignoresSafeArea()
                        .onTapGesture { isShowing.toggle() }
                        .transition(.opacity)
                }
                
                HStack {
                    VStack(alignment: .leading, spacing: 16) {
                        ForEach(DrawerMenuNavigation.allCases, id: \.self) { menu in
                            DrawerButtonView(
                                menu: menu,
                                selectedMenu: selectedButton,
                                onClick: { newMenu in
                                    selectedButton = newMenu
                                    onMenuClick(menu)
                                    isShowing.toggle()
                                }
                            )
                        }
                        Spacer()
                    }
                    .padding()
                    .frame(width: proxy.size.width * drawerWidth, alignment: .leading)
                    .background(ColorTheme.surfaceContainerLow)
                    .offset(x: isShowing ? 0 : -proxy.size.width * drawerWidth)
                    .animation(.easeInOut(duration: 0.3), value: isShowing)
                    .transition(.slide)
                    
                    Spacer()
                }
            }
        }
    }
}

#Preview {
    DrawerMenuView(isShowing: .constant(true), onMenuClick: { _ in })
}
