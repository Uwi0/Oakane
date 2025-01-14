import SwiftUI

struct SplashScreen: View {
    @State private var isLogoVisible = false
    @State private var isTextVisible = false
    @State private var shouldNavigate = false
    @EnvironmentObject private var navigation: AppNavigation
    
    private static let alreadyReadKey = UserDefaultsKeys.onBoardingAlreadyRead
    @AppStorage(alreadyReadKey) private var isAlreadyRead: Bool = UserDefaults.standard.bool(forKey: alreadyReadKey)
    
    var body: some View {
        ZStack {
            ColorTheme.surface
                .ignoresSafeArea()
                .opacity(isLogoVisible ? 1 : 0)
                .animation(.easeIn(duration: 0.6), value: isLogoVisible)
            
            VStack {
                Image(ImageConstants.shirokoIcon)
                    .resizable()
                    .frame(width: 240, height: 240)
                    .clipShape(Circle())
                    .scaleEffect(isLogoVisible ? 1 : 1.2)
                    .opacity(isLogoVisible ? 1 : 0)
                    .animation(.easeOut(duration: 0.8).delay(0.2), value: isLogoVisible)
                
                Text("Oakane")
                    .font(Typography.displaySmall)
                    .opacity(isTextVisible ? 1 : 0)
                    .animation(.easeIn(duration: 1.0).delay(0.5), value: isTextVisible)
            }
            
        }
        .onAppear {
            startSplashAnimation()
        }
        .onChange(of: shouldNavigate) {
            DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
                if isAlreadyRead {
                    if navigation.navPath.isEmpty {
                        navigation.navigate(to: .home)
                    }
                } else {
                    if navigation.navPath.isEmpty {
                        navigation.navigate(to: .onboarding)
                    }
                }
            }
        }
    }
    
    private func startSplashAnimation() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.3) {
            isLogoVisible = true
        }
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.8) {
            isTextVisible = true
        }
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
            shouldNavigate = true
        }
    }
}
