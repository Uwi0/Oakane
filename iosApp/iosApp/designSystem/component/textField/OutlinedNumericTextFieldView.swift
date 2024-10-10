import SwiftUI

struct OutlinedNumericTextFieldView: View {
    @Binding var value: String
    var placeHolder: String = "Placeholder"
    
    @State private var borderColor = ColorTheme.outline
    
    var body: some View {
        TextField(
            placeHolder,
            text: $value,
            onEditingChanged: { isEditChange in
                borderColor = isEditChange ? ColorTheme.primary : ColorTheme.outline
            }
        )
        .keyboardType(.numberPad)
        .onChange(of: value) { newValue in
            let filteredValue = newValue.filter(\.isNumber)
            if filteredValue != newValue {
                value = filteredValue
            }
        }
        .tint(ColorTheme.onSurfaceVariant)
        .autocorrectionDisabled(true)
        .padding(12)
        .overlay {
            RoundedRectangle(cornerRadius: 12).stroke(borderColor, lineWidth: .init(2))
        }
    }
}

#Preview {
    OutlinedNumericTextFieldView(value: .constant("Value"))
}
