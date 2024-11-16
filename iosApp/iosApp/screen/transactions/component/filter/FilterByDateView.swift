import SwiftUI
import Shared

struct FilterByDateView: View {
    
    @State private var date: Date = Date()
    let onEvent: (TransactionsEvent) -> Void
    
    var body: some View {
        VStack(spacing: 16) {
            Text("Filter by Date")
                .font(Typography.titleSmall)
            
            DatePicker("Date", selection: $date, displayedComponents: [.date])
                .tint(ColorTheme.outline)
                .datePickerStyle(GraphicalDatePickerStyle())
            
            Spacer()
            
            FilledButtonView(
                text: "Apply Filter",
                onClick: {onEvent(.FilterByDate(timeMillis: date.toInt64()))}
            )
                .frame(height: 48)
        }
        .padding(.vertical, 24)
        .padding(.horizontal, 16)
        
    }
}

#Preview {
    FilterByDateView(onEvent: { _ in })
}
