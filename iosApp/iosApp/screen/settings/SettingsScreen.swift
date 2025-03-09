import SwiftUI
import Shared

struct SettingsScreen: View {
    
    @EnvironmentObject private var navigation: AppNavigation
    @StateObject private var viewModel: SettingsViewModel = SettingsViewModel()
    @AppStorage(UserDefaultsKeys.isDarkMode) private var darkAppearance: Bool = false
    @State private var viewController: UIViewController?
    @State private var isFileImportedPresented: Bool = false
    @State private var isSheetPresented: Bool = false
    
    var body: some View {
        VStack {
            
            NavigationBar()
            VStack {
                ButtonCurrencyView()
                Toggle("Dark Appearance", isOn: $darkAppearance)
                Divider()
                ButtonSettingsView(
                    title: "Back Up",
                    iconName: "arrow.triangle.2.circlepath",
                    onClick: { viewModel.handle(event: .GenerateBackupFile())}
                )
                ButtonSettingsView(
                    title: "Import Data",
                    iconName: "square.and.arrow.down",
                    onClick: { isFileImportedPresented = true }
                )
                Spacer()
            }
            .padding(.vertical, 24)
            .padding(.horizontal, 16)
        }
        .navigationBarBackButtonHidden(true)
        .background(ColorTheme.surface.ignoresSafeArea())
        .background(ViewControllerAccessor { vc in viewController = vc} )
        .onAppear(perform: viewModel.initData)
        .onChange(of: viewModel.uiEffect) { observe(effect: viewModel.uiEffect) }
        .fileImporter(
            isPresented: $isFileImportedPresented,
            allowedContentTypes: [.json],
            onCompletion: { result in
                switch result {
                case .success(let url): readBackup(url: url)
                case .failure(let error): print("error \(error)")
                }
            }
        )
        .sheet(isPresented: $isSheetPresented) {
            SelectCurrencyContentView(
                onConfirm: { selectedCurrency in
                    viewModel.handle(event: .ChangeCurrency(currency: selectedCurrency))
                },
                currency: viewModel.uiState.currentCurrency
            )
        }
        
    }
    
    @ViewBuilder
    private func NavigationBar() -> some View {
        VStack {
            NavigationTopAppbar(title: "Settings", onAction: { navigation.navigateBack() })
            Divider()
        }
    }
    
    @ViewBuilder
    private func ButtonCurrencyView() -> some View {
        OutlinedContentButtonView(
            onClick: { isSheetPresented = true },
            content: {
                Image(systemName: "dollarsign.circle").resizable().frame(width: 24, height: 24)
                Text("Set Currency")
                Spacer()
                Text("\(viewModel.uiState.currentCurrency.name)")
                Image(systemName: "chevron.right")
            }
        )
    }
    
    private func observe(effect: SettingsEffect?) {
        if let safeEffect = effect {
            switch onEnum(of: safeEffect) {
            case .confirm(_):
                print("clicked")
            case .generateBackupFile(let effect):
                saveBackup(json: effect.json)
            case .navigateBack:
                print("Navigate back")
            case .restoreBackupFile:
                print("restire back up")
            case .showError(let effect):
                print(effect.message)
            case .successChangeCurrency:
                isSheetPresented = false
            case .openDrawer:
                print("open Drawer")
            }
        }
        viewModel.uiEffect = nil
    }
    
    private func saveBackup(json: String) {
        let formatDate = "dd-MM-yyyy-HH-mm"
        let date = DateUtilsKt.getCurrentDateWith(format: formatDate)
        let fileName = "Backup-Oakane-\(date).json"
        if let safeViewController = viewController {
            saveDocument(value: json, fileName: fileName, viewController: safeViewController)
        }
    }
    
    private func readBackup(url: URL) {
        let result = readJSONFileAsString(fileUrl: url)
        switch result {
        case .success(let json):
            viewModel.handle(event: .RetrieveBackupFile(json: json))
        case .failure(let error):
            print("error \(error)")
        }
    }
}

#Preview {
    SettingsScreen()
}
