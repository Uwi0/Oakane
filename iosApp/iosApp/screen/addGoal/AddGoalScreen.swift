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
            Text("Hello world")
            VStack(spacing: 16) {
                GoalImagePickerView()
                
                Spacer()
                    .frame(height: 4)
                
                OutlinedCurrencyTextFieldView(
                    label: "Target",
                    onValueChange: { amount in }
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

