import SwiftUI

struct GoalHeaderView: View {
    
    let isVisible: Bool
    let onClick: () -> Void
    
    var body: some View {
        if isVisible {
            AddGoalView(onClick: onClick)
        } else {
            SimplifiedAddGoalView(onClick: onClick)
        }
    }
}

#Preview {
    GoalHeaderView(isVisible: true, onClick: {})
}
