import SwiftUI

struct CardTimeView: View {
    
    let uiState: GoalState
    
    private let datePattern = "dd MMM yyy"
    
    private var startDate: String {
        uiState.startDate.formatDateWith(pattern: datePattern)
    }
    
    private var endDate: String {
        uiState.endDate.formatDateWith(pattern: datePattern)
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            HStack(spacing: 8) {
                CardContentWithIconView(
                    icon: "calendar",
                    title: "Start Date",
                    content: startDate
                )
                .frame(maxWidth: .infinity, alignment: .leading)
                
                Divider()
                    .frame(height: 30)
                
                CardContentWithIconView(
                    icon: "calendar.badge.checkmark",
                    title: "Start Date",
                    content: endDate
                )
                .frame(maxWidth: .infinity, alignment: .leading)
            }
            Text("\(uiState.daysLeft) Days Left")
                .foregroundStyle(ColorTheme.error)
                .font(Typography.labelSmall)
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}

#Preview {
    let state = GoalState()
    CardTimeView(uiState: state)
}
