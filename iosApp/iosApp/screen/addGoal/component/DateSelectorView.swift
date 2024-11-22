import SwiftUI

struct DateSelectorView: View {
    
    let img: String
    let text: String
    let onSelectedDate: (Int64) -> Void
    
    private let imgSize: CGFloat = 24
    @State private var selectedDate = Date()
    
    var body: some View {
        HStack(spacing: 8) {
            OutlinedCircleIcon(imageName: img, size: imgSize)
            Text(text)
                .font(Typography.titleSmall)
            Spacer()
            Text(selectedDate.formmatTo("dd MMM yyyy"))
                .font(Typography.titleSmall)
        }
        .overlay {
            DatePickerView(date: $selectedDate, scaleEffect: 3)
        }
        .onChange(of: selectedDate){ newValue in
            let convertedDate = selectedDate.toInt64()
            onSelectedDate(convertedDate)
        }
    }
}

#Preview {
    DateSelectorView(img: "calendar", text: "Start Date", onSelectedDate: { _ in })
}
