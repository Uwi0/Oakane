import SwiftUI

struct DateButtonView: View {
    
    let title: String
    let label: String
    let onClick: (Date) -> Void
    
    init(title: String, label: String = "", onClick: @escaping (Date) -> Void) {
        self.title = title
        self.label = label
        self.onClick = onClick
    }
    
    @State private var selectedDate: Date = Date()

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            if (!label.isEmpty) {
                Text(label).font(Typography.titleMedium)
            }
            DateButtonContent()
        }
        
    }
    
    @ViewBuilder
    private func DateButtonContent() -> some View {
        HStack(alignment: .center, spacing: 16) {
            Text(selectedDate.formmatTo("dd MMM yyyy"))
                .font(Typography.bodyLarge)
                .foregroundStyle(ColorTheme.outline)
            Spacer()
            Image(systemName: "calendar")
        }
        .outlinedTextStyle(borderColor: ColorTheme.outline)
        .overlay {
            DatePickerView(date: $selectedDate.animation(), scaleEffect: 3)
        }
        .onChange(of: selectedDate) {
            onClick(selectedDate)
        }
    }
}

#Preview {
    DateButtonView(title: "Today", label: "Date",onClick: { _ in })
}
