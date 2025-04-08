import SwiftUI

struct FilterButtonDateView: View {
    let title: String
    @Binding var date: Date
    @State private var showingDatePicker: Bool = false
    var body: some View {
            Button(action: { showingDatePicker.toggle() }) {
                ButtonContent()
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 8)
            .frame(maxWidth: .infinity)
            .background(ColorTheme.surface)
            .overlay {
                RoundedRectangle(cornerRadius: 8).stroke(ColorTheme.primary, lineWidth: 2)
            }
            .overlay {
                DatePickerView(date: $date, scaleEffect: 3)
            }
            .buttonStyle(PlainButtonStyle())
    }
    
    @ViewBuilder
    private func ButtonContent() -> some View {
        HStack {
            VStack(spacing: 8) {
                Text(title).font(Typography.labelLarge)
                Text(date.formatted(date: .abbreviated, time: .omitted)).font(Typography.labelLarge)
            }
            Spacer()
            Image(systemName: "calendar")
        }
    }
}

#Preview {
    FilterButtonDateView(title: "Hello world", date: .constant(Date()))
}
