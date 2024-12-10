import SwiftUI

struct SimplifiedAddGoalView: View {
    let onClick: () -> Void
    var body: some View {
        HStack {
            Text("Add Goal")
                .font(Typography.titleMedium)
            Spacer()
            IconButtonView(name: "plus", width: 24, onClick: onClick).padding(.trailing, 12)
        }
    }
}

#Preview {
    SimplifiedAddGoalView {
        
    }
}
