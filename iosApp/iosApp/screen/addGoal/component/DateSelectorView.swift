import SwiftUI

struct DateSelectorView: View {
    
    let img: String
    let text: String
    let selectedDate: Int64
    let onSelectedDate: (Int64) -> Void
    
    private let imgSize: CGFloat = 24
    @State private var currentDate = Date()
    
    private var textDate: String {
        if selectedDate == 0 {
            return currentDate.formmatTo("dd MMM yyyy")
        } else {
            return selectedDate.formatDateWith(pattern: "dd MMM yyyy")
        }
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(text).font(Typography.titleSmall)
            HStack(spacing: 16) {
                Text(textDate)
                    .font(Typography.titleSmall)
                    .foregroundStyle(ColorTheme.outline)
                Spacer()
                Image(systemName: img)
            }
            .padding(16)
            .background(RoundedRectangle(cornerRadius: 16).stroke(ColorTheme.outline, lineWidth: 2))
            .overlay {
                DatePickerView(date: $currentDate, scaleEffect: 3)
            }
            .onChange(of: currentDate){ newValue in
                let convertedDate = currentDate.toInt64()
                onSelectedDate(convertedDate)
            }
        }
    }
}

#Preview {
    DateSelectorView(img: "calendar", text: "Start Date", selectedDate: 0,onSelectedDate: { _ in })
}
