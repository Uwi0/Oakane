import SwiftUI

struct CurrencyTextFieldAdapter: UIViewRepresentable {
    
    @Binding var value: Int
    let formatter: NumberFormatter
    var onFocusChange: ((Bool) -> Void) = { _ in }

    func makeUIView(context: Context) -> CurrencyUITextField {
        let textField = CurrencyUITextField(formatter: formatter)
        textField.onValueChange = { newValue in
            DispatchQueue.main.async {
                value = newValue
            }
        }
        textField.onFocusChange = onFocusChange
        return textField
    }

    func updateUIView(_ uiView: CurrencyUITextField, context: Context) {
        let decimalValue = Decimal(value)
        uiView.text = formatter.string(for: decimalValue)
    }
}
