import SwiftUI

struct AddGoalScreen: View {
    
    @StateObject private var viewModel = AddGoalViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    private let toolbarContent = ToolBarContent(title: "Add Goal")
    private let imgSize: CGFloat = 120
    
    private var uiState: AddGoalState {
        viewModel.uiState
    }
    
    var body: some View {
        ZStack {
            ColorTheme.surface.ignoresSafeArea()
            VStack(spacing: 16) {
                GoalImagePickerView()
                
                Spacer()
                    .frame(height: 4)
                
                OutlinedCurrencyTextFieldView(
                    label: "Target",
                    onValueChange: { amount in }
                )
                
                OutlinedTextFieldView(
                    value: uiState.goalName,
                    placeHolder: "Goal Name" ,
                    onValueChange: { goalName in }
                )
                
                OutlinedTextFieldView(
                    value: uiState.note,
                    placeHolder: "Note",
                    onValueChange: { note in }
                )
                
                DateSelectorView(
                    img: "calendar",
                    text: "Start Date",
                    date: uiState.startDate.formatDateWith(pattern: "dd MMM yyyy"),
                    onSelectedDate: { startDate in }
                )
                
                DateSelectorView(
                    img: "calendar.badge.checkmark",
                    text: "End Date",
                    date: uiState.endDate.formatDateWith(pattern: "dd MMM yyyy"),
                    onSelectedDate: { endDate in }
                )
                
                Spacer()
                
                FilledButtonView(text: "Save Goal", onClick: {})
                    .frame(height: 48)
            }
            .padding(.vertical, 24)
            .padding(.horizontal, 24)
        }
        .navigationBarBackButtonHidden(true)
        .customToolbar(content: toolbarContent, onEvent: onToolbarEvent)
    }
    
    private func onToolbarEvent(_ event: ToolbarEvent) {
        navigation.navigateBack()
    }
}

#Preview {
    AddGoalScreen()
}

