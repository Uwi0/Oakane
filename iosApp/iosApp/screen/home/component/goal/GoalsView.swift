import SwiftUI
import Shared

struct GoalsView: View {
    let goals: [GoalModel]
    let onClick: (GoalModel) -> Void
    
    private var takenGoals: [GoalModel] {
        GoalModelKt.swiftTake(goals, n: 3)
    }
    
    var body: some View {
        VStack(spacing: 8) {
            ForEach(takenGoals, id: \.id){ goal in
                GoalItemView(goal: goal)
                    .onTapGesture {
                        onClick(goal)
                    }
            }
        }
    }
}
