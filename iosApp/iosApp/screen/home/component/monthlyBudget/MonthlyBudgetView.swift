import SwiftUI

struct MonthlyBudgetView: View {
    var body: some View {
        VStack(spacing: 16) {
            TopContentView()
            HorizontalDivider()
            BottomContentView()
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}

#Preview {
    MonthlyBudgetView()
}
