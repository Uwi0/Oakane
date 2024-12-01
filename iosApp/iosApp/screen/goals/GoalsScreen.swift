import SwiftUI

struct GoalsScreen: View {
    
    @StateObject private var viewModel: GoalsViewModel = GoalsViewModel()
    
    private var uiState: GoalsState {
        viewModel.uiState
    }
    
    var body: some View {
        GeometryReader { proxy in
            ColorTheme.surface.ignoresSafeArea()
            VStack{
                GoalsTopAppBar(onSearch: { query in viewModel.handle(event: .FilterBy(query: query))})
                ScrollView {
                    VStack(spacing: 16) {
                        ForEach(uiState.goals, id: \.self) { goal in
                            GoalItemView(goal: goal)
                        }
                    }
                    .padding(16)
                }
                .scrollIndicators(.hidden)
                
            }
            .frame(width: proxy.size.width, height: proxy.size.height)
            
            FabButtonView(
                size: FabConstant.size,
                xPos: proxy.size.width - FabConstant.xOffset,
                yPos: proxy.size.height - FabConstant.yOffset,
                onClick: {}
            )
        }
        .navigationBarBackButtonHidden(true)
        
    }
}

#Preview {
    GoalsScreen()
}
