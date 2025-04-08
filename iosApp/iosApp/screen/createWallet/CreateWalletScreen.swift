import SwiftUI
import Shared

struct CreateWalletScreen: View {
    
    let walletId: Int64
    let fromOnBoarding: Bool
    
    @StateObject private var viewModel = CreateWalletViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    var body: some View {
        VStack {
            CreateWalletTopAppBar()
            VStack(spacing: 16) {
                NameAndIconContentView()
                StartWithBalanceContentView()
                NoteContentView()
                HorizontalColorSelectorView(
                    colors: colors,
                    selectedColor: Binding(
                        get: { viewModel.uiState.toColor() },
                        set: { color in viewModel.handle(event: .SelectedColor(hex: color.toHexString())) }
                    )
                )
                Spacer()
                SaveButton()
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 24)
        }
        .background(ColorTheme.surface)
        .navigationBarBackButtonHidden(true)
        .sheet(
            isPresented: Binding(
                get: { viewModel.uiState.isSheetShown },
                set: { viewModel.handle(event: .ShowSheet(content: .icon, shown: $0)) }
            ),
            content: {
                SelectIconView(
                    selectedIcon: Binding(
                        get: { viewModel.uiState.selectedIconName},
                        set: { viewModel.handle(event: .SelectedIcon(name: $0))}
                    ),
                    selectedColor: viewModel.uiState.toColor(),
                    onTakeImage: { viewModel.handle(event: .SelectedImage(file: $0)) },
                    onConfirm: { viewModel.handle(event: .ShowSheet(content: .icon, shown: false)) }
                )
            }
        )
        .task {
            viewModel.initData(walletId: walletId)
        }
        .onChange(of: viewModel.uiEffect) {
            observe(effect: viewModel.uiEffect)
        }
    }
    
    @ViewBuilder
    private func CreateWalletTopAppBar() -> some View {
        VStack {
            NavigationTopAppbar(
                title: "Create Wallet",
                onAction: { viewModel.handle(event: .NavigateBack())}
            )
            Divider()
        }
    }
    
    @ViewBuilder
    private func NameAndIconContentView() -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Create Wallet")
                .font(Typography.titleMedium)
            
            HStack(alignment: .center, spacing: 8) {
                SelectedIconView(
                    imageName: viewModel.uiState.selectedImageFile,
                    icon: viewModel.uiState.selectedIconName,
                    color: viewModel.uiState.toColor()
                ).onTapGesture {
                    viewModel.handle(event: .ShowSheet(content: .icon, shown: true))
                }
                
                OutlinedTextFieldView(
                    value: Binding(
                        get: { viewModel.uiState.walletName },
                        set: { viewModel.handle(event: .WalletNameChanged(name: $0)) }
                    ),
                    placeHolder: "Wallet Name",
                    showLabel: false
                )
            }
        }
    }
    
    @ViewBuilder
    private func StartWithBalanceContentView() -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Start Balance")
                .font(Typography.titleMedium)
            
            OutlinedCurrencyTextFieldView(
                value: Binding(
                    get: { viewModel.uiState.toBalance() },
                    set: { balance in viewModel.handle(event: balance.toBalanceChanged())}),
                onValueChange: { _ in }
            )
        }
    }
    
    @ViewBuilder
    private func NoteContentView() -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Note")
                .font(Typography.titleMedium)
            OutlinedTextFieldView(
                value: Binding(
                    get: { viewModel.uiState.note },
                    set: { viewModel.handle(event: .NoteChanged(note: $0))}),
                placeHolder: "note...",
                showLabel: false
            )
            
        }
    }
    
    @ViewBuilder
    private func SaveButton() -> some View {
        FilledContentButtonView(
            onClick: { viewModel.handle(event: .SaveWallet())},
            content: { Text("Save Wallet")}
        )
    }
    
    private func observe(effect: CreateWalletEffect?) {
        guard let effect = effect else { return }
        switch onEnum(of: effect) {
        case .navigateBack: navigation.navigateBack()
        case .showError(let effect): print("Error \(effect.message)")
        case .successCreateWallet: onSuccess()
        }
        
        viewModel.uiEffect = nil
    }
    
    private func onSuccess() {
        if fromOnBoarding {
            navigation.navigate(to: .home)
        } else {
            navigation.navigateBack()
        }
    }
}

#Preview {
    CreateWalletScreen(walletId: 0, fromOnBoarding: false)
}
