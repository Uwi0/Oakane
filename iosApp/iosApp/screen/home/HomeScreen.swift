import SwiftUI
import Shared

struct HomeScreen: View {
    @Binding var showDrawer: Bool
    
    @StateObject private var viewModel: HomeViewModel = HomeViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    private var uiState: HomeState {
        viewModel.uiState
    }
    
    var body: some View {
        GeometryReader { geometryReader in
            ColorTheme.surface
                .ignoresSafeArea(.all)
            VStack {
                TopAppBarView()
                ContentView()
            }
            FabButtonView(
                size: FabConstant.size,
                xPos: geometryReader.size.width - FabConstant.xOffset,
                yPos: geometryReader.size.height - FabConstant.yOffset,
                onClick: { navigation.navigate(to: .addTransaction(transactionId: 0)) }
            )
        }
        .navigationBarBackButtonHidden(true)
        .onAppear {
            viewModel.initViewModel()
        }
        .onChange(of: viewModel.uiEffects){
            observe(effect:viewModel.uiEffects)
        }
    }
    
    @ViewBuilder
    private func TopAppBarView() -> some View {
        HStack(alignment: .center,spacing: 16) {
            Image(systemName: "line.3.horizontal")
                .tint(ColorTheme.onSurface)
                .font(.title)
                .onTapGesture { viewModel.handle(event: .OpenDrawer()) }
            
            Text("Dashboard")
                .font(Typography.titleMedium)
        }
        .padding(.horizontal, 16)
        .frame(maxWidth: .infinity, alignment: .leading)
    }
    
    @ViewBuilder
    private func ContentView() -> some View {
        ScrollView {
            VStack(alignment: .leading,spacing: 16) {
                WalletBalanceView(wallet: uiState.wallet)
                MonthlyBudgetView(monthlyBudgetOverView: uiState.monthlyBudgetOverView, onEvent: viewModel.handle(event:))
                Text("RecentTransaction")
                    .font(Typography.titleMedium)
                HomeTransactionsView(transactions: uiState.transactions)
                if uiState.transactions.count > 3 {
                    ShowMoreItemView(onClick: { viewModel.handle(event: .ToTransactions())})
                }
                GoalHeaderView(isVisible: true, onClick: { viewModel.handle(event: .ToCreateGoal()) })
                GoalsView(
                    goals: uiState.goals,
                    onClick: { goal in viewModel.handle(event: .ToGoalWith(id: goal.id))}
                )
                ShowMoreItemView(onClick: {})
            }
            .padding(.vertical, 24)
            .padding(.horizontal, 16)
            .ignoresSafeArea(.all)
        }
        .scrollIndicators(.hidden)
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
                navigation.navigate(to: .addGoal(goalId: 0))
            case .showError(let error):
                print("error \(error.message)")
            case .toGoalWith(let homeEvent):
                navigation.navigate(to: .goal(goalId: homeEvent.id))
            case .toGoals:
                navigation.navigate(to: .goals)
            case .toMonthlyBudget:
                navigation.navigate(to: .monthlyBudget)
            case .toTransaction(let effect):
                navigation.navigate(to: .transaction(transactionId: effect.id))
            case .toWallets:
                navigation.navigate(to: .wallets)
            }
        }
        viewModel.uiEffects = nil
    }
}

#Preview {
    HomeScreen(showDrawer: .constant(false))
}
