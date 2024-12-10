import SwiftUI

struct OutlinedNumericTextFieldView: View {
    
    let onValueChange: (Double?) -> Void
    let placeHolder: String
    
    @State private var value: Double?
    @State private var rawValue: String = ""
    @State private var borderColor = ColorTheme.outline
    
    @FocusState private var isFocused: Bool
    
    private let numberFormatter: NumberFormatter
    
    init(initialValue: Double?, placeHolder: String, onValueChange: @escaping (Double?) -> Void) {
        self.onValueChange = onValueChange
        self.placeHolder = placeHolder
        self._value = State(initialValue: initialValue)
        self._rawValue = State(initialValue: initialValue.map { NumberFormatter().currencyString(from: $0) } ?? "")
        
        numberFormatter = NumberFormatter()
        numberFormatter.numberStyle = .currency
        numberFormatter.maximumFractionDigits = 3
        numberFormatter.locale = .current
    }
    
    var body: some View {
        TextField(placeHolder, text: $rawValue)
            .keyboardType(.decimalPad)
            .tint(ColorTheme.onSurfaceVariant)
            .autocorrectionDisabled(true)
            .padding(12)
            .overlay {
                RoundedRectangle(cornerRadius: 12)
                    .stroke(borderColor, lineWidth: 2)
            }
            .focused($isFocused)
            .onChange(of: rawValue) {
                // Allow typing unformatted value
                if let doubleValue = Double(rawValue.filter(\.isNumber)) {
                    value = doubleValue / 100.0
                } else {
                    value = nil
                }
                onValueChange(value)
            }
    }
}

extension NumberFormatter {
    func currencyString(from value: Double) -> String {
        string(from: NSNumber(value: value)) ?? ""
    }
}

#Preview {
    OutlinedNumericTextFieldView(initialValue: 5000.0,placeHolder: "Hello world", onValueChange: { _ in })
}
