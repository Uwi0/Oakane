import SwiftUI

struct MonthlyBudgetScreen: View {
    @State private var searchQuery = ""
    var body: some View {
        ZStack {
            ColorTheme.surface.ignoresSafeArea()
            VStack {
                MonthlyBudgetTopbarView(title: "Monthly Budget", onNavigateBack: {})
                VStack(alignment: .leading, spacing: 24) {
                    MonthlyBudgetTopContentView()
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.vertical, 24)
                .padding(.horizontal, 16)
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        }
        .navigationBarBackButtonHidden(true)
    }
}

private struct MonthlyBudgetTopbarView: View {
    
    let title: String
    let onNavigateBack: () -> Void
    
    var body: some View {
        VStack {
            NavigationTopAppbar(title: title, navigateBack: onNavigateBack)
            Divider()
        }
    }
}


#Preview {
    MonthlyBudgetScreen()
}
