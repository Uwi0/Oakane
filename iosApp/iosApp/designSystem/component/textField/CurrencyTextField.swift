import SwiftUI
import UIKit
import Shared

struct CurrencyTextField: View {
    @Binding var value: Int
    
    private let formatter: NumberFormatter
    @FocusState private var isFocused: Bool
    
    private var label: String {
        let mag = pow(10, formatter.maximumFractionDigits)
        return formatter.string(for: Decimal(value) / mag) ?? ""
    }
    
    private var borderColor: Color {
        isFocused ? ColorTheme.primary : ColorTheme.outline
    }
    
    public init(value: Binding<Int>, currency: Currency) {
        let formatter = NumberFormatter()
        let identifier = "\(currency.languageCode)_\(currency.countryCode)"
        formatter.numberStyle = .currency
        formatter.minimumFractionDigits = currency.baseMultiplier == 1 ? 2 : 0
        formatter.maximumFractionDigits = currency.baseMultiplier == 1 ? 2 : 0
        formatter.locale = Locale(identifier: identifier)
        self._value = value
        self.formatter = formatter
    }
    
    public var body: some View {
        Button(action: {
            isFocused = true
        }) {
            HStack {
                TextFieldView()
            }
            .outlinedTextStyle(borderColor: borderColor)
        }
        .buttonStyle(PlainButtonStyle())
    }
    
    @ViewBuilder
    private func TextFieldView() -> some View {
        ZStack {
            Text(label)
                .layoutPriority(1)
                .frame(maxWidth: .infinity, alignment: .leading)
            CurrencyInputField(value: $value, formatter: formatter)
                .frame(maxWidth: .infinity)
        }
        .focused($isFocused)
    }
}

#Preview {
    @Previewable @State var value: Int = 0
    @Previewable @State var someValue: String = ""
    VStack {
        OutlinedTextFieldView(value: $someValue)
        CurrencyTextField(value: $value, currency: .idr)
    }
}

class CustomTextField: UITextField {
    override func canPerformAction(_ action: Selector, withSender sender: Any?) -> Bool {
        false
    }
    
    override func selectionRects(for range: UITextRange) -> [UITextSelectionRect] {
        []
    }
    
    override func caretRect(for position: UITextPosition) -> CGRect {
        super.caretRect(for: position)
    }
}

struct CurrencyInputField: UIViewRepresentable {
    @Binding var value: Int
    var formatter: NumberFormatter
    
    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }
    
    func makeUIView(context: Context) -> CustomTextField {
        let textField = CustomTextField()
        textField.delegate = context.coordinator
        textField.keyboardType = .numberPad
        textField.tintColor = .clear
        textField.textColor = .clear
        textField.textAlignment = .left
        textField.backgroundColor = .clear
        textField.font = .systemFont(ofSize: 32, weight: .regular)
        
        textField.addTarget(
            context.coordinator,
            action: #selector(Coordinator.editingChanged(textField:)),
            for: .editingChanged
        )
        
        context.coordinator.updateText(value, textField: textField)
        return textField
    }
    
    func updateUIView(_ uiView: CustomTextField, context: Context) {}
    
    class Coordinator: NSObject, UITextFieldDelegate {
        private var input: CurrencyInputField
        
        private var lastValidInput: String? = ""
        
        init(_ currencyTextField: CurrencyInputField) {
            self.input = currencyTextField
        }
        
        func setValue(_ value: Int, textField: UITextField) {
            input.value = value
            updateText(value, textField: textField)
        }
        
        func updateText(_ value: Int, textField: UITextField) {
            textField.text = String(value)
            lastValidInput = String(value)
        }
        
        func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
            if string.isEmpty {
                if input.value == 0 {
                    textField.resignFirstResponder()
                }
                
                setValue(Int(input.value / 10), textField: textField)
            }
            return true
        }
        
        @objc func editingChanged(textField: CustomTextField) {
            guard var oldText = lastValidInput else {
                return
            }
            
            let char = (textField.text ?? "").first { next in
                if oldText.isEmpty || next != oldText.removeFirst() {
                    return true
                }
                return false
            }
            
            guard let char = char, let digit = Int(String(char)) else {
                textField.text = lastValidInput
                return
            }
            
            let (multValue, multOverflow) = input.value.multipliedReportingOverflow(by: 10)
            if multOverflow {
                textField.text = lastValidInput
                return
            }
            
            let (addValue, addOverflow) = multValue.addingReportingOverflow(digit)
            if addOverflow {
                textField.text = lastValidInput
                return
            }
            
            if input.formatter.maximumFractionDigits + input.formatter.maximumIntegerDigits < String(addValue).count {
                textField.text = lastValidInput
                return
            }
            
            setValue(addValue, textField: textField)
        }
    }
}
