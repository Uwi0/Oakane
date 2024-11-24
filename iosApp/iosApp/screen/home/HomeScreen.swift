import SwiftUI
import Shared

struct HomeScreen: View {
    @Binding var showDrawer: Bool
    
    @StateObject private var viewModel: HomeViewModel = HomeViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    var body: some View {
        GeometryReader { geometryReader in
            ZStack {
                ColorTheme.surface
                    .ignoresSafeArea(.all)
                
                VStack {
                    HomeTopBarView(
                        onClick: {
                            viewModel.handle(event: .OpenDrawer())
                        }
                    )
                    HomeContentView(
                        uiState: viewModel.uiState,
                        onEvent: viewModel.handle(event:)
                    )
                }
                
                FabButtonView(
                    size: 56,
                    xPos: geometryReader.size.width - FabConstant.xOffset,
                    yPos: geometryReader.size.height - FabConstant.yOffset,
                    onClick: {
                        navigation.navigate(to: .addTransaction(transactionId: 0))
                    }
                )
            }
        }
        .onAppear {
            viewModel.initViewModel()
        }
        .onChange(of: viewModel.uiEffects, perform: observe(effect:))
    }
    
    private func observe(effect: HomeEffect?) {
        if let safeEffect = effect {
            switch onEnum(of: safeEffect) {
            case .openDrawer:
                showDrawer = !showDrawer
            case .toCreateTransaction:
                navigation.navigate(to: .addTransaction(transactionId: 0))
            case .toTransactions:
                navigation.navigate(to: .transactions)
            case .toCreateGoal:
                navigation.navigate(to: .addGoal)
            case .showError(let error):
                print("error \(error.message)")
            case .toGoal(_):
                print("toGoal")
            }
        }
        viewModel.uiEffects = nil
    }
}
