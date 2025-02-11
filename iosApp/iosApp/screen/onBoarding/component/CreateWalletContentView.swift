import SwiftUI
import Shared

struct CreateWalletContentView: View {
    
    let onEvent: (OnBoardingEvent) -> Void
    @State private var isSheetPresented: Bool = false
    @State private var sheetContent: WalletSheetContent = .create
    @State private var imageFile: String = ""
    @State private var selectedIcon: CategoryIconName = .wallet
    @State private var selectedColorHex: String = colorsSelector.first ?? "0xFF4CAF50"
    @State private var walletName: String = ""
    @State private var startBalance: Int = 0
    
    private var bottomSheetSize: PresentationDetent {
        switch sheetContent {
        case .create: return .fraction(0.50)
        case .selectIcon: return .fraction(0.9)
        case .selectColor: return .large
        }
    }
    
    private var walletModel: WalletModel {
        WalletModel(
            id: 0,
            currency: Currency.usd,
            balance: Double(startBalance),
            name: walletName,
            isDefaultIcon: imageFile.isEmpty,
            icon: imageFile.isEmpty ? selectedIcon.displayName : imageFile,
            color: selectedColorHex,
            note: ""
        )
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            Text("Create Your Wallet").font(Typography.displaySmall)
            Spacer().frame(height: 148)
            ImageView()
            Spacer().frame(height: 20)
            Text("You can create your own wallet or skip this step, and the system will automatically create a default wallet for you.")
                .font(Typography.titleMedium)
            Spacer()
            CreateWalletButton(onClick: { isSheetPresented = true})
            Spacer().frame(height: 16)
            SkipButton(onClick: { onEvent(.SkippWallet())})
        }
        .padding(.vertical, 24)
        .padding(.horizontal, 16)
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
        .background(ColorTheme.surface.ignoresSafeArea())
        .sheet(isPresented: $isSheetPresented) {
            SheetContentView()
                .presentationDetents([bottomSheetSize])
                .presentationDragIndicator(.visible)
        }
    }
    
    @ViewBuilder
    private func ImageView() -> some View {
        VStack(alignment: .center) {
            Image(ImageConstants.monaEmptyWallet)
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
        FilledContentButtonView(onclick: onClick) {
            Spacer().frame(width: 24,height: 24)
            Spacer()
            Text("Skip")
            Spacer()
            Image(systemName: "chevron.right").fontWeight(.bold)
        }
    }
    
    @ViewBuilder
    private func SheetContentView() -> some View {
        switch sheetContent {
        case .create: CreateWalletSheetContent()
        case .selectColor: Text("World")
        case .selectIcon: SelectIconSheetContent()
        }
    }
    
    @ViewBuilder
    private func CreateWalletSheetContent() -> some View {
        let onWalletEvent: (CreateWalletEvent) -> Void = { walletEvent in
            switch walletEvent {
            case .changeWallet(name: let name): walletName = name
            case .selectedIcon: sheetContent = .selectIcon
            case .changeStart(balance: let balance): print(balance)
            case .selectWallet(color: let color): selectedColorHex = color
            case .saveWallet:
                onEvent(.ConfirmWallet(wallet: walletModel))
                isSheetPresented = false
            }
        }
        
        CreateWalletSheetView(
            imageFile: imageFile,
            selectedIcon: selectedIcon,
            selectedColor: selectedColorHex.toColorLong(),
            walletName: $walletName,
            startBalance: $startBalance,
            colors: colorsSelector,
            onEvent: onWalletEvent
        )
    }
    
    @ViewBuilder
    private func SelectIconSheetContent() -> some View {
        SelectIconView(
            selectedIcon: selectedIcon,
            selectedColor: selectedColorHex.toColorLong(),
            onPickIcon: { icon in selectedIcon = icon},
            onTakImage: { file in imageFile = file},
            onConfirm: { sheetContent = .create }
        )
    }
}

#Preview {
    CreateWalletContentView(onEvent: { _ in })
}
