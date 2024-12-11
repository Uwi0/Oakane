import SwiftUI
import Shared

struct CategoryLimitItemView: View {
    
    let categoryLimit: CategoryLimitModel
    
    private var limit: String {
        categoryLimit.limit.toFormatIDRCurrency()
    }
    
    private var progress: String {
        categoryLimit.progress.asPercentageString()
    }
    
    private var spent: String {
        categoryLimit.spent.toFormatIDRCurrency()
    }
    
    var body: some View {
        HStack(spacing: 16) {
            CategoryLimitIconView(categoryLimit: categoryLimit)
            VStack(alignment: .leading, spacing: 8) {
                HStack {
                    Text(categoryLimit.name)
                        .font(Typography.titleMedium)
                    Spacer()
                    Text(limit)
                        .font(Typography.titleMedium)
                }
                ProgressIndicatorView(value: categoryLimit.progress)
                Text("spent: \(spent) / \(progress)")
                    .font(Typography.labelSmall)
                    .foregroundStyle(ColorTheme.outline)
            }
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}

private struct CategoryLimitIconView: View {
    
    let categoryLimit: CategoryLimitModel
    
    var body: some View {
        if categoryLimit.isDefault {
            let icon = categoryLimit.iconName.asIconCategory()
            let color = Color(hex: categoryLimit.formattedColor)
            CategoryIconView(icon: icon, color: color)
        } else {
            DisplayImageFileView(
                fileName: categoryLimit.fileName,
                width: 48,
                height: 48
            )
        }
    }
}

#Preview(traits: .sizeThatFitsLayout) {
    CategoryLimitItemView(categoryLimit: CategoryLimitModel.companion.EMPTY)
}
