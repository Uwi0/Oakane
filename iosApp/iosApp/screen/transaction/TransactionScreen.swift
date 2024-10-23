import SwiftUI

struct TransactionScreen: View {
    
    @EnvironmentObject private var navigation: AppNavigation
    
    var body: some View {
        ZStack {
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                HStack(spacing: 24) {
                    IconButtonView(
                        name: "arrow.left",
                        size: 16,
                        onClick: navigation.navigateBack
                    )
                    Text("Transaction")
                        .font(Typography.titleMedium)
                    Spacer()
                }
                .padding(.horizontal, 16)
                Spacer()
            }
        }
        .navigationBarBackButtonHidden(true)
    }
}

#Preview {
    TransactionScreen()
}
