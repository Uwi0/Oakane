import SwiftUI
import Shared


struct CategoryItemView: View {
    
    let category: CategoryModel
    
    var body: some View {
        HStack(spacing: 16) {
            let iconCategory = category.name.asIconCategory()
            CategoryIconView(icon: iconCategory, color: Color(hex: category.color))
            Text(category.name)
            Spacer()
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}
