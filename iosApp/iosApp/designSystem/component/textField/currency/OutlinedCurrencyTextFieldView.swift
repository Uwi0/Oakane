import SwiftUI

struct OutlinedCurrencyTextFieldView: View {
    @State private var value = 0
    
    private var numberFormatter: NumberFormatter
    @FocusState private var isFocused: Bool
    @State private var borderColor = ColorTheme.outline
    
    init(numberFormatter: NumberFormatter = NumberFormatter()) {
        self.numberFormatter = numberFormatter
        self.numberFormatter.numberStyle = .currency
        self.numberFormatter.maximumFractionDigits = 2
    }
    
    var body: some View {
        CurrencyTextFieldAdapter(numberFormatter: numberFormatter, value: $value)
            .padding(.horizontal, 16)
            .padding(.vertical, 8)
            .overlay(RoundedRectangle(cornerRadius: 16)
                .stroke(borderColor, lineWidth: 2))
            .frame(height: 56)
            .onChange(of: isFocused) { isEditing in
                        borderColor = isEditing ? ColorTheme.primary : ColorTheme.outline
                    }
    }
}

#Preview {
    OutlinedCurrencyTextFieldView()
}
