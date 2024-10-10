
import SwiftUI

struct DatePickerView: View {
    
    @Binding var date: Date
    let scaleEffect: CGFloat
    
    var body: some View {
        DatePicker(
            "",
            selection: $date,
            displayedComponents: [.date]
        )
        .labelsHidden()
        .blendMode(.destinationOver)
        .tint(ColorTheme.outline)
        .scaleEffect(x:scaleEffect)
        .frame(maxWidth: .infinity)
        .clipped()
    }
}

#Preview {
    DatePickerView(date: .constant(Date()), scaleEffect: 1)
}
