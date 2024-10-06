import SwiftUI

struct MonthlyBudgetView: View {
    var body: some View {
        VStack {
            Text("Monthly Budget")
            Divider()
            Text("income and expense content")
        }
        .customBackground(backgroundColor: surface)
    }
}

#Preview {
    MonthlyBudgetView()
}
