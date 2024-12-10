import SwiftUI

struct DateButtonView: View {
    
    let title: String
    let onClick: (Date) -> Void
    
    @State private var selectedDate: Date = Date()

    var body: some View {
        HStack(alignment: .center, spacing: 16) {
            Text(selectedDate.formmatTo("dd MMM yyyy"))
                .font(Typography.bodyLarge)
                .foregroundStyle(ColorTheme.outline)
            Spacer()
            Image(systemName: "calendar")
        }
        .padding(16)
        .background(RoundedRectangle(cornerRadius: 16).stroke(ColorTheme.outline, lineWidth: 2))
        .overlay {
            DatePickerView(date: $selectedDate.animation(), scaleEffect: 3)
        }
        .onChange(of: selectedDate) {
            onClick(selectedDate)
        }
    }
}

#Preview {
    DateButtonView(title: "Today", onClick: { _ in })
}
