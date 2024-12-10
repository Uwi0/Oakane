import SwiftUI
import Shared

struct MonthlyBudgetBottomContentView: View {
    
    let onEvent: (MonthlyBudgetEvent) -> Void
    
    var body: some View {
        VStack {
            CategoryLimitHeaderView(onClick: { onEvent(.Dialog(shown: true))})
            CategoryLimitContentView(categoryLimits: [])
        }
    }
}

private struct CategoryLimitHeaderView: View {
    
    let onClick: () -> Void
    
    var body: some View {
        HStack(alignment: .center) {
            Text("Categories Limit")
                .font(Typography.bodyLarge)
            Spacer()
                .frame(maxWidth: .infinity, maxHeight: 20)
            IconButtonView(name: "plus", size: 24, onClick: onClick)
        }
    }
}


private struct CategoryLimitContentView: View {
    
    let categoryLimits: [CategoryLimitModel]
    
    var body: some View {
        ScrollView {
            ForEach(categoryLimits, id: \.id){ categoryLimit in
                CategoryLimitItemView(categoryLimit: categoryLimit)
            }
        }
    }
}

#Preview {
    MonthlyBudgetBottomContentView(onEvent: { _ in })
}
