import SwiftUI

struct SplashScreen: View {
    @State private var isLogoVisible = false
    @State private var isTextVisible = false
    @State private var shouldNavigate = false
    @EnvironmentObject private var navigation: AppNavigation
    @StateObject private var viewModel = SplashViewModel()

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
        .task {
            viewModel.initData()
        }
        .onAppear {
            startSplashAnimation()
        }
        .onChange(of: shouldNavigate) {
            if shouldNavigate {
                navigateToDestinationScreen()
            }
        }
    }
    
    private func navigateToDestinationScreen() {
        DispatchQueue.main.async {
            if viewModel.termsAndServiceAlreadyRead {
                if viewModel.obBoardingAlreadyRead {
                    navigation.navigate(to: .home)
                } else {
                    navigation.navigate(to: .onboarding)
                }
            } else {
                navigation.navigate(to: .termAndService)
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
            if !shouldNavigate {
                shouldNavigate = true
            }
        }
    }
}
