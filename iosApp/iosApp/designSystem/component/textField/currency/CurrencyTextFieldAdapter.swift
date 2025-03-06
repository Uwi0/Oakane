import SwiftUI

struct CurrencyTextFieldAdapter: UIViewRepresentable {
    @Binding var value: Int
    let formatter: NumberFormatter
    var onFocusChange: ((Bool) -> Void) = { _ in }
    
    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }

    func makeUIView(context: Context) -> CurrencyUITextField {
        let textField = CurrencyUITextField(formatter: formatter)
        textField.delegate = context.coordinator
        textField.onValueChange = { newValue in
            DispatchQueue.main.async {
                if self.value != newValue {
                    self.value = newValue
                }
            }
        }
        textField.onFocusChange = onFocusChange
        
        let initialDecimal = Decimal(value)
        textField.text = formatter.string(for: initialDecimal)
        
        return textField
    }

    func updateUIView(_ uiView: CurrencyUITextField, context: Context) {
        if uiView.intValue != value {
            let decimalValue = Decimal(value)
            uiView.text = formatter.string(for: decimalValue)
            uiView.intValue = value
        }
    }
    
    class Coordinator: NSObject, UITextFieldDelegate {
        var parent: CurrencyTextFieldAdapter
        
        init(_ parent: CurrencyTextFieldAdapter) {
            self.parent = parent
        }
    }
}
