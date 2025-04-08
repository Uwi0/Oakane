import SwiftUI
import Shared

struct TermAndServiceScreen: View {
    
    private let TERM_TITLE = TermAndServiceMessage.shared.TERM_TITLE
    private let TERM_1 = TermAndServiceMessage.shared.TERM_1
    private let TERM_2 = TermAndServiceMessage.shared.TERM_2
    private let TERM_3 = TermAndServiceMessage.shared.TERM_3
    private let TERM_4 = TermAndServiceMessage.shared.TERM_4
    
    @StateObject private var viewModel = TermAndServiceViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private var uiState: TermAndServiceState {
        viewModel.uiState
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            TopAppbar(title: "Importand User Agreement")
            TermAndServiceContent()
            Spacer()
        }
        .background(ColorTheme.surface.ignoresSafeArea())
        .navigationBarBackButtonHidden(true)
        .task {
            viewModel.initData()
        }
    }
    
    @ViewBuilder
    private func TermAndServiceContent() -> some View {
        VStack(spacing: 16) {
            GithubButton()
            Text(TERM_TITLE)
                .font(Typography.titleMedium)
            TermAndServiceCheckBox(
                isChecked: Binding(
                    get: { uiState.isTerm1Checked },
                    set: { isChecked in viewModel.handle(event: .OnTerm1Checked(isChecked: isChecked))}
                ),
                title: TERM_1
            )
            TermAndServiceCheckBox(
                isChecked: Binding(
                    get: { uiState.isTerm2Checked },
                    set: { isChecked in viewModel.handle(event: .OnTerm2Checked(isChecked: isChecked))}
                ),
                title: TERM_2
            )
            TermAndServiceCheckBox(
                isChecked: Binding(
                    get: { uiState.isTerm3Checked },
                    set: { isChecked in viewModel.handle(event: .OnTerm3Checked(isChecked: isChecked))}
                ),
                title: TERM_3
            )
            TermAndServiceCheckBox(
                isChecked: Binding(
                    get: { uiState.isTerm4Checked },
                    set: { isChecked in viewModel.handle(event: .OnTerm4Checked(isChecked: isChecked))}
                ),
                title: TERM_4
            )
            Spacer()
            ConfirmTermAndServiceButton()
        }
        .padding(.vertical, 24)
        .padding(.horizontal, 16)
        .onChange(of: viewModel.uiEffect) {
            observe(effect: viewModel.uiEffect)
        }
    }
    
    @ViewBuilder
    private func GithubButton() -> some View {
        Link(destination: URL(string: "https://github.com/Uwi0/Oakane")!) {
            HStack {
                GithubIcon()
                    .frame(width: 48, height: 48)
                VStack(alignment: .leading, spacing: 8) {
                    Text("Oakane is open source")
                        .font(Typography.titleMedium)
                    Text("https://github.com/Uwi0/Oakane")
                        .font(Typography.bodyMedium)
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .customBackground(backgroundColor: ColorTheme.surface)
        }
        .buttonStyle(PlainButtonStyle())
    }
    
    @ViewBuilder
    private func TermAndServiceCheckBox(isChecked: Binding<Bool>, title: String) -> some View {
        Toggle(isOn: isChecked) {
            Text(title).font(Typography.bodyMedium)
        }
        .toggleStyle(CheckboxToggleStyle())
    }
    
    @ViewBuilder
    private func ConfirmTermAndServiceButton() -> some View {
        FilledContentButtonView(onClick: { viewModel.handle(event: .OnAgreementButtonClicked())}, enabled: uiState.isButtonEnabled) {
            Text("I Accept and Agree")
        }
    }
    
    private func observe(effect: TermAndServiceEffect?) {
        guard let effect else { return }
        
        switch onEnum(of: effect) {
        case .navigateToOnBoarding: navigation.navigate(to: .onboarding)
        case .showError(let effect): print(effect.message)
        }
        
        viewModel.uiEffect = nil
    }
}

#Preview {
    TermAndServiceScreen()
}
