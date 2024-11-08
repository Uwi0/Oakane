import SwiftUI
import Shared


struct CategoryItemView: View {
    
    let category: CategoryModel
    
    var body: some View {
        HStack(spacing: 16) {
            let iconCategory = category.iconName.asIconCategory()
            if category.isDefault {
                CategoryIconView(icon: iconCategory, color: Color(hex: category.formattedColor))
            } else {
                DisplayImageFileView(fileName: category.icon, width: 48, height: 48)
                    .overlay{
                        Circle()
                            .stroke(Color(hex: category.formattedColor), lineWidth: 3)
                    }
            }
            Text(category.name)
            Spacer()
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}
