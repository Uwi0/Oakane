import SwiftUI

struct GoalHeaderView: View {
    
    let isVisible: Bool
    
    var body: some View {
        if isVisible {
            AddGoalView()
        } else {
            SimplifiedAddGoalView()
        }
    }
}

#Preview {
    GoalHeaderView(isVisible: true)
}
