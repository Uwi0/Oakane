import SwiftUI

struct CurrencyTextFieldAdapter: UIViewRepresentable {
    
    @Binding var value: Int
    let formatter: NumberFormatter

    func makeUIView(context: Context) -> CurrencyUITextField {
        let textField = CurrencyUITextField(formatter: formatter)
        textField.onValueChange = { newValue in
            DispatchQueue.main.async {
                value = newValue
            }
        }
        return textField
    }

    func updateUIView(_ uiView: CurrencyUITextField, context: Context) {
        let decimalValue = Decimal(value) / 100
        uiView.text = formatter.string(for: decimalValue)
    }
}
