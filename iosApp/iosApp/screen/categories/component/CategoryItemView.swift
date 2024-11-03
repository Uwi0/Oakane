import SwiftUI
import Shared


struct CategoryItemView: View {
    
    let category: CategoryModel
    
    var body: some View {
        HStack(spacing: 16) {
            let iconCategory = category.iconName.asIconCategory()
            CategoryIconView(icon: iconCategory, color: Color(hex: category.formattedColor))
            Text(category.name)
            Spacer()
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}
