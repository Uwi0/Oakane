import SwiftUI
import Shared

struct ReportItemView: View {
    
    let item: ReportModel
    
    private var color: Color {
        item.isDefault ? Color.primary : Color.error
    }
    
    var body: some View {
        HStack(alignment: .center, spacing: 16) {
            SelectedIconView(imageName: item.icon, icon: item.iconName, color: item.formattedColor)
            Text(item.name).font(Typography.titleMedium)
            Spacer().frame(height: 8)
            Text(item.amount.toFormatIDRWithCurrency()).font(Typography.titleMedium).foregroundStyle(color)
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}
