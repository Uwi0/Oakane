import SwiftUI

struct FilterByDateView: View {
    @State private var date: Date = Date()
    let onSelectedDate: (Int64) -> Void
    var body: some View {
        VStack(spacing: 16) {
            Text("Filter by Date")
                .font(Typography.titleSmall)
            
            DatePicker("Date", selection: $date, displayedComponents: [.date])
                .tint(ColorTheme.outline)
                .datePickerStyle(GraphicalDatePickerStyle())
            
            Spacer()
            
            FilledButtonView(text: "Apply Filter", onClick: {onSelectedDate(Int64(date.timeIntervalSince1970) * 1000)})
                .frame(height: 48)
        }
        .padding(.vertical, 24)
        .padding(.horizontal, 16)
        
    }
}

#Preview {
    FilterByDateView(onSelectedDate: {_ in})
}
