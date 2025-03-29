import SwiftUI
import Shared

struct SettingsScreen: View {
    
    @Binding var openDrawer: Bool
    var showDrawer: Bool = false
    @EnvironmentObject private var navigation: AppNavigation
    @StateObject private var viewModel: SettingsViewModel = SettingsViewModel()
    @AppStorage(UserDefaultsKeys.isDarkMode) private var darkAppearance: Bool = false
    @State private var viewController: UIViewController?
    @State private var isFileImportedPresented: Bool = false
    @State private var isSheetPresented: Bool = false
    
    var body: some View {
        VStack {
            NavigationBar()
            VStack(spacing: 16) {
                ButtonCurrencyView()
                Toggle("Dark Appearance", isOn: $darkAppearance)
                Toggle(
                    "Recurring Monthly Budget",
                    isOn: Binding(
                        get: { viewModel.uiState.isRecurringBudget},
                        set: { viewModel.handle(event: .ToggleRecurringBudget(isRecurring: $0))}
                    )
                )
                Toggle(
                    "Recurring Category Limit",
                    isOn: Binding(
                        get: { viewModel.uiState.isRecurringCategoryLimit },
                        set: { viewModel.handle(event: .ToggleRecurringCategoryLimit(isRecurring: $0))}
                    )
                )
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
                Divider()
                ButtonNavigationSettingsView(
                    icon: "bell",
                    title: "Reminder",
                    onClick: requestNotificationPermission
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
                currency: viewModel.uiState.currency
            )
        }
        
    }
    
    @ViewBuilder
    private func NavigationBar() -> some View {
        VStack {
            NavigationTopAppbar(
                title: "Settings",
                showDrawer: showDrawer,
                onAction: onAction
            )
            Divider()
        }
    }
    
    private func onAction() {
        if showDrawer {
            viewModel.handle(event: .OpenDrawer())
        } else {
            viewModel.handle(event: .NavigateBack())
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
                Text("\(viewModel.uiState.currency.name)")
                Image(systemName: "chevron.right")
            }
        )
    }
    
    private func observe(effect: SettingsEffect?) {
        guard let effect else { return }
        
        switch onEnum(of: effect) {
        case .confirm(_): print("clicked")
        case .generateBackupFile(let effect): saveBackup(json: effect.json)
        case .navigateBack: navigation.navigateBack()
        case .restoreBackupFile: print("restire back up")
        case .showError(let effect): print(effect.message)
        case .successChangeCurrency: isSheetPresented = false
        case .openDrawer: openDrawer = !openDrawer
        case .navigateToReminder: navigation.navigate(to: .reminder)
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
        case .success(let json): viewModel.handle(event: .RetrieveBackupFile(json: json))
        case .failure(let error): print("error \(error)")
        }
    }
    
    private func requestNotificationPermission() {
        let center = UNUserNotificationCenter.current()
        center.requestAuthorization(options: [.alert, .badge, .sound]) { granted, error in
            if granted {
                viewModel.handle(event: .NavigateToReminder())
            } else {
                print("Permission denied")
            }
        }
    }
}

#Preview {
    SettingsScreen(openDrawer: .constant(false))
}
