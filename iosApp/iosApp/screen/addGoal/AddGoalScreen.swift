import SwiftUI
import Shared

struct AddGoalScreen: View {
    
    let goalId: Int64
    
    @StateObject private var viewModel = AddGoalViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    private let imgSize: CGFloat = 120
    
    private var uiState: AddGoalState {
        viewModel.uiState
    }
    
    var body: some View {
        VStack(spacing: 16) {
            GoalImagePickerView(
                imageUrl: uiState.fileName,
                onSelectedFile: { name in viewModel.handle(event: .SetFile(name: name))}
            )
            
            Spacer()
                .frame(height: 4)
            
            OutlinedCurrencyTextFieldView(
                label: "Target",
                value: $viewModel.uiState.targetAmount,
                onValueChange: { amount in viewModel.handle(event: .SetTarget(amount: amount))}
            )
            
            OutlinedTextFieldView(
                value: $viewModel.uiState.goalName,
                placeHolder: "Goal Name" ,
                onValueChange: { goalName in viewModel.handle(event: .SetName(value: goalName))}
            )
            
            OutlinedTextFieldView(
                value: $viewModel.uiState.note,
                placeHolder: "Note",
                onValueChange: { note in viewModel.handle(event: .SetNote(value: note))}
            )
            
            DateSelectorView(
                img: "calendar",
                text: "Start Date",
                selectedDate: uiState.startDate,
                onSelectedDate: { startDate in viewModel.handle(event: .SetStart(date: startDate))}
            )
            
            DateSelectorView(
                img: "calendar.badge.checkmark",
                text: "End Date",
                selectedDate: uiState.endDate,
                onSelectedDate: { endDate in viewModel.handle(event: .SetEnd(date: endDate))}
            )
            
            Spacer()
            
            FilledButtonView(text: "Save Goal", onClick: { viewModel.handle(event: .SaveGoal())} )
                .frame(height: 48)
        }
        .padding(.vertical, 24)
        .padding(.horizontal, 24)
        .background(content: {ColorTheme.surface.ignoresSafeArea()})
        .onAppear(perform: { viewModel.initData(goalId: goalId) } )
        .navigationBarBackButtonHidden(true)
        .onChange(of: viewModel.uiEffect){
            observeEffect(effect:viewModel.uiEffect)
        }
    }
    
    private func onToolbarEvent(_ event: ToolbarEvent) {
        navigation.navigateBack()
    }
    
    private func observeEffect(effect: AddGoalEffect?) {
        if let safeEffect = effect {
            switch onEnum(of: safeEffect){
            case .navigateBack:
                navigation.navigateBack()
            case .showError(let errorMsg):
                print(errorMsg)
            case .successSaveGoal:
                navigation.navigateBack()
            }
        }
        viewModel.uiEffect = nil
    }
}

#Preview {
    AddGoalScreen(goalId: 0)
}

