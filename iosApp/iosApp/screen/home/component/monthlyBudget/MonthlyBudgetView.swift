import SwiftUI
import Shared

struct MonthlyBudgetView: View {
    
    let onEvent: (HomeEvent) -> Void
    
    var body: some View {
        VStack(spacing: 16) {
            TopContentView(onEvent: onEvent)
            HorizontalDivider()
            BottomContentView()
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}

#Preview {
    MonthlyBudgetView(onEvent: { _ in })
}
