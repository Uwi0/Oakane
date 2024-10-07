import SwiftUI
import Shared

struct GoalsView: View {
    let goals: [GoalModel]
    
    private var takenGoals: [GoalModel] {
        GoalModelKt.swiftTake(goals, n: 3)
    }
    
    var body: some View {
        VStack(spacing: 8) {
            ForEach(takenGoals, id: \.id){ goal in
                GoalItemView(goal: goal)
            }
        }
    }
}

#Preview {
    let goalDefault = GoalModelKt.dummyGoals()
    GoalsView(goals: goalDefault)
}
