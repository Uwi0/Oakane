import SwiftUI
import Shared

struct ButtonSelectCategoryLimitView: View {
    let category: CategoryModel
    let onClick: () -> Void
    var body: some View {
        Button(action: onClick){
            HStack(spacing: 16) {
                ButtonCategoryLimitLeadingIcon(category: category)
                Text(category.name)
                    .font(Typography.titleMedium)
                    .foregroundStyle(ColorTheme.onSurface)
                Spacer()
                Image(systemName: "chevron.right")
                    .fontWeight(.bold)
                    .foregroundColor(ColorTheme.outline)
            }
            .frame(maxWidth: .infinity)
            .padding(.vertical, 16)
            .padding(.horizontal, 16)
            .background(
                RoundedRectangle(cornerRadius: 16).stroke(
                    ColorTheme.outline,
                    lineWidth: 2
                )
            )
        }
    }
}

private struct ButtonCategoryLimitLeadingIcon: View {
    
    let category: CategoryModel
    
    var body: some View {
        if category.isDefault {
            let icon = category.iconName.asIconCategory()
            Image(systemName: icon)
                .foregroundStyle(ColorTheme.outline)
                .fontWeight(.bold)
                .frame(width: 24, height: 24)
        } else {
            DisplayImageFileView(fileName: category.icon, width: 24, height: 24)
        }
    }
}


#Preview {
    ButtonSelectCategoryLimitView(category: defaultCategoryModel,onClick: {})
        .padding()
}
