import SwiftUI

struct OutlinedCurrencyTextFieldView: View {
    
    private let label: String
    private let onValueChange: (String) -> Void
    private var numberFormatter: NumberFormatter
    
    @Binding var value: Int
    @FocusState private var isFocused: Bool
    @State private var borderColor = ColorTheme.outline
    
    init(
        label: String,
        value: Binding<Int>,
        onValueChange: @escaping (String) -> Void,
        numberFormatter: NumberFormatter = NumberFormatter()
    ) {
        self.label = label
        self.onValueChange = onValueChange
        self.numberFormatter = numberFormatter
        self._value = value
        self.numberFormatter.numberStyle = .currency
        self.numberFormatter.maximumFractionDigits = 2
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(label)
                .font(Typography.titleSmall)
            CurrencyTextFieldAdapter(value: $value, formatter: numberFormatter)
                .padding(.horizontal, 16)
                .padding(.vertical, 8)
                .overlay(RoundedRectangle(cornerRadius: 16)
                .stroke(borderColor, lineWidth: 2))
                .frame(height: 56)
                .onChange(of: value) { newValue in
                    onValueChange(String(newValue))
                }
        }
        
    }
}

#Preview {
    OutlinedCurrencyTextFieldView(label: "Target", value: .constant(5000),onValueChange: { _ in })
}
