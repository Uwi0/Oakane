import SwiftUI

struct CreateWalletContentView: View {
    var body: some View {
        VStack(alignment: .leading) {
            Text("Create Your Wallet").font(Typography.displaySmall)
            Spacer().frame(height: 148)
            ImageView()
            Spacer().frame(height: 20)
            Text("You can create your own wallet or skip this step, and the system will automatically create a default wallet for you.")
                .font(Typography.titleMedium)
            Spacer()
            CreateWalletButton(onClick: {})
            SkipButton(onClick: {})
        }
        .padding(.vertical, 24)
        .padding(.horizontal, 16)
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
        .background(ColorTheme.surface.ignoresSafeArea())
    }
    
    @ViewBuilder private func ImageView() -> some View {
        VStack(alignment: .center) {
            Image(ImageConstants.monaEmptyWallet)
                .resizable()
                .clipShape(Circle())
                .frame(width: 220, height: 220)
        }
        .frame(maxWidth: .infinity, alignment: .center)
    }
    
    @ViewBuilder private func CreateWalletButton(onClick: @escaping () -> Void) -> some View {
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
    
    @ViewBuilder private func SkipButton(onClick: @escaping () -> Void) -> some View {
        FilledContentButtonView(onclick: onClick) {
            Spacer().frame(width: 24,height: 24)
            Spacer()
            Text("Create Wallet")
            Spacer()
            Image(systemName: "chevron.right").fontWeight(.bold)
        }
    }
}

#Preview {
    CreateWalletContentView()
}
