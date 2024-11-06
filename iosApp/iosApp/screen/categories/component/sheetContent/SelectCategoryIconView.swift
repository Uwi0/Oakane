import SwiftUI
import Shared

struct SelectCategoryIconView: View {
    
    let onEvent: (CategoriesEvent) -> Void
    @State private var iconName: CategoryIconName? = nil
    
    var body: some View {
        VStack{
            Text("Chose Icon")
                .font(Typography.titleMedium)
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    ForEach(parentCategories, id: \.name){ parentCategory in
                        SelectionIconItemView(
                            parentCategory: parentCategory,
                            onEvent: { categoryName in iconName = categoryName }
                        )
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                    
                }
            }
            .accessibilityHidden(true)
            
            FilledButtonView(
                text: "Select Icon",
                onClick: {
                    if let iconName {
                        onEvent(.SelectedIcon(name: iconName))
                    }
                }
            )
            .frame(height: 48)
        }
        .padding(16)
    }
}

private struct SelectionIconItemView: View {
    let parentCategory: ParentCategory
    let onEvent: (CategoryIconName) -> Void
    
    var body: some View {
        VStack(spacing: 8) {
            SelectionHeaderView(parentCategory: parentCategory)
            CategoryIconContentView(parentCategory: parentCategory, onEvent: onEvent)
        }
    }
}

private struct SelectionHeaderView: View {
    
    let parentCategory: ParentCategory
    
    var body: some View {
        HStack(alignment: .center) {
            Text(parentCategory.displayName)
                .font(Typography.titleSmall)
                .padding(.horizontal, 8)
            Spacer()
        }
        .frame(maxWidth: .infinity)
    }
}

private struct CategoryIconContentView: View {
    let parentCategory: ParentCategory
    let onEvent: (CategoryIconName) -> Void
    private let column: [GridItem] = [.init(.adaptive(minimum: 48, maximum: 48))]
    
    var body: some View {
        LazyVGrid(columns: column, alignment: .leading) {
            let categories = categoriesName(parentCategory: parentCategory)
            ForEach(categories, id: \.self) { category in
                SelectionIconView(category: category, onEvent: onEvent)
            }
        }
    }
}

struct SelectionIconView: View {
    let category: CategoryIconName
    let onEvent: (CategoryIconName) -> Void

    var body: some View {
        Button(action: { onEvent(category) }) {
            Image(systemName: category.asIconCategory())
                .resizable()
                .scaledToFit()
                .foregroundStyle(ColorTheme.outline)
        }
        .buttonStyle(PlainButtonStyle())
        .frame(width: 36, height: 36)
    }
}

#Preview {
    SelectCategoryIconView { _ in
        
    }
}
