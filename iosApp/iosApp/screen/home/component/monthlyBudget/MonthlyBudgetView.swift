import SwiftUI

struct MonthlyBudgetView: View {
    var body: some View {
        VStack {
            MonthlyBudgetTopContent()
            Divider()
            Text("income and expense content")
        }
        .customBackground(backgroundColor: surface)
    }
}

#Preview {
    MonthlyBudgetView()
}
