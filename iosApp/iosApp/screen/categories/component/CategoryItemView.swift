import SwiftUI
import Shared


struct CategoryItemView: View {
    
    let category: CategoryModel
    
    var body: some View {
        HStack(spacing: 16) {
            let iconCategory = category.name.asIconCategory()
            CategoryIconView(icon: iconCategory.icon, color: iconCategory.color)
            Text(category.name)
            Spacer()
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}
