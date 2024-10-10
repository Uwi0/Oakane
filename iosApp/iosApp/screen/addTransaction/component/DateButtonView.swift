import SwiftUI

struct DateButtonView: View {
    @Binding var date: Date
    let title: String
    let onClick: () -> Void
    
    
    var body: some View {
        HStack(alignment: .center, spacing: 16) {
            Text(date.formatted())
                .font(Typography.bodyLarge)
                .foregroundStyle(ColorTheme.outline)
            Spacer()
            Image(systemName: "calendar")
        }
        .padding(16)
        .background(RoundedRectangle(cornerRadius: 16).stroke(ColorTheme.outline, lineWidth: 2))
        .overlay {
            DatePickerView(date: $date, scaleEffect: 3)
        }
        
    }
}

#Preview {
    DateButtonView(date: .constant(Date()),title: "Today", onClick: {})
}
