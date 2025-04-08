import SwiftUI
import Shared

struct CreateWalletContentView: View {
    
    let onEvent: (OnBoardingEvent) -> Void
    
    var body: some View {
        VStack(alignment: .leading) {
            Text("Create Your Wallet").font(Typography.displaySmall)
            Spacer().frame(height: 148)
            ImageView()
            Spacer().frame(height: 20)
            Text("You can create your own wallet or skip this step, and the system will automatically create a default wallet for you.")
                .font(Typography.titleMedium)
            Spacer()
            CreateWalletButton(onClick: { onEvent(.NavigateToCreateWallet()) })
            Spacer().frame(height: 16)
            SkipButton(onClick: { onEvent(.SkippWallet())})
        }
        .padding(.vertical, 24)
        .padding(.horizontal, 16)
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
        .background(ColorTheme.surface.ignoresSafeArea())
    }
    
    @ViewBuilder
    private func ImageView() -> some View {
        VStack(alignment: .center) {
            Image(ImageConstants.oakaneIcon)
                .resizable()
                .clipShape(Circle())
                .frame(width: 220, height: 220)
        }
        .frame(maxWidth: .infinity, alignment: .center)
    }
    
    @ViewBuilder
    private func CreateWalletButton(onClick: @escaping () -> Void) -> some View {
        OutlinedContentButtonView(onClick: onClick) {
            Image(systemName: "wallet.bifold")
                .resizable()
                .frame(width: 24, height: 24)
                .fontWeight(.bold)
            Spacer()
            Text("Create Wallet")
            Spacer()
            Spacer().frame(width: 24,height: 24)
        }
    }
    
    @ViewBuilder
    private func SkipButton(onClick: @escaping () -> Void) -> some View {
        FilledContentButtonView(onClick: onClick) {
            Spacer().frame(width: 24,height: 24)
            Spacer()
            Text("Skip")
            Spacer()
            Image(systemName: "chevron.right").fontWeight(.bold)
        }
    }
}

#Preview {
    CreateWalletContentView(onEvent: { _ in })
}
