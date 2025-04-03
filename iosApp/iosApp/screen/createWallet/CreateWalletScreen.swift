import SwiftUI
import Shared

struct CreateWalletScreen: View {
    
    let walletId: Int64
    
    @StateObject private var viewModel = CreateWalletViewModel()
    
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
        .task {
            viewModel.initData(walletId: walletId)
        }
        
    }
    
    @ViewBuilder
    private func CreateWalletTopAppBar() -> some View {
        VStack {
            NavigationTopAppbar(title: "Create Wallet", onAction: { viewModel.handle(event: .NavigateBack())})
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
                )
                
                OutlinedTextFieldView(
                    value: Binding(
                        get: { viewModel.uiState.walletName },
                        set: { viewModel.handle(event: .WalletNameChanged(name: $0))}
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
            onclick: { viewModel.handle(event: .SaveWallet())},
            content: { Text("Save Wallet")}
        )
    }
}

#Preview {
    CreateWalletScreen(walletId: 0)
}
