import SwiftUI

struct SplashScreen: View {
    var body: some View {
        VStack(alignment: .center, spacing: 16) {
            
        }
        .padding(.vertical, 24)
        .padding(.horizontal, 16)
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
        .background(ColorTheme.surface.ignoresSafeArea())
    }
}

#Preview {
    SplashScreen()
}
