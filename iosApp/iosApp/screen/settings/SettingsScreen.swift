import SwiftUI
import Shared

struct SettingsScreen: View {
    
    @EnvironmentObject private var navigation: AppNavigation
    @StateObject private var viewModel: SettingsViewModel = SettingsViewModel()
    @State private var viewController: UIViewController?
    @State private var isPresented: Bool = false
    
    var body: some View {
        VStack {
            NavigationBar()
            VStack {
                Divider()
                ButtonSettingsView(
                    title: "Back Up",
                    iconName: "arrow.triangle.2.circlepath",
                    onClick: { viewModel.handle(event: .GenerateBackupFile())}
                )
                ButtonSettingsView(
                    title: "Import Data",
                    iconName: "square.and.arrow.down",
                    onClick: { isPresented = true }
                )
                Spacer()
            }
            .padding(.vertical, 24)
            .padding(.horizontal, 16)
        }
        .navigationBarBackButtonHidden(true)
        .background(ColorTheme.surface.ignoresSafeArea())
        .background(ViewControllerAccessor { vc in viewController = vc} )
        .onChange(of: viewModel.uiEffect) { observe(effect: viewModel.uiEffect) }
        .fileImporter(
            isPresented: $isPresented,
            allowedContentTypes: [.json],
            onCompletion: { result in
                switch result {
                case .success(let url): readBackup(url: url)
                case .failure(let error): print("error \(error)")
                }
            }
        )
    }
    
    @ViewBuilder func NavigationBar() -> some View {
        VStack {
            NavigationTopAppbar(title: "Settings", navigateBack: { navigation.navigateBack() })
            Divider()
        }
    }
    
    private func observe(effect: SettingsEffect?) {
        if let safeEffect = effect {
            switch onEnum(of: safeEffect) {
            case .confirm(let effect):
                print("clicked")
            case .generateBackupFile(let effect):
                saveBackup(json: effect.json)
            case .navigateBack:
                print("Navigate back")
            case .restoreBackupFile:
                print("restire back up")
            case .showError(let effect):
                print(effect.message)
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
