import SwiftUI
import Shared

struct ReportItemView: View {
    
    let item: ReportModelKt
    
    private var currency: Currency {
        item.currency
    }
    
    private var color: Color {
        item.isDefault ? Color.primary : Color.error
    }
    
    var body: some View {
        HStack(alignment: .center, spacing: 16) {
            SelectedIconView(imageName: item.icon, icon: item.iconName, color: item.formattedColor)
            Text(item.name).font(Typography.titleMedium)
            Spacer()
            Text(item.amount.toFormatCurrency(currency: currency)).font(Typography.titleMedium).foregroundStyle(color)
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}
