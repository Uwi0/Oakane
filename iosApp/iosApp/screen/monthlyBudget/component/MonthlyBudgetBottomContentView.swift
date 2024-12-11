import SwiftUI
import Shared

struct MonthlyBudgetBottomContentView: View {
    
    let isEditMode: Bool
    let categoryLimits: [CategoryLimitModel]
    let onEvent: (MonthlyBudgetEvent) -> Void
    
    var body: some View {
        VStack {
            if isEditMode {
                CategoryLimitHeaderView(onClick: { onEvent(.Dialog(shown: true))})
                CategoryLimitContentView(
                    categoryLimits: categoryLimits,
                    onClick: { categoryLimit in onEvent(.Selected(categoryLimit: categoryLimit))}
                )
            } else {
                Text("you may set some expense limits for each category, after adding your budget")
                    .font(Typography.titleMedium)
            }
            
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
            IconButtonView(name: "plus", width: 24, onClick: onClick)
        }
    }
}


private struct CategoryLimitContentView: View {
    
    let categoryLimits: [CategoryLimitModel]
    let onClick: (CategoryLimitModel) -> Void
    
    var body: some View {
        ScrollView {
            ForEach(categoryLimits, id: \.id){ categoryLimit in
                CategoryLimitItemView(categoryLimit: categoryLimit)
                    .onTapGesture { onClick(categoryLimit) }
            }
        }
    }
}

#Preview {
    MonthlyBudgetBottomContentView(isEditMode: false,categoryLimits: [],onEvent: { _ in })
}
