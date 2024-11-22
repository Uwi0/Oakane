import SwiftUI

class CurrencyUITextField: UITextField {
    private let formatter: NumberFormatter
    var onValueChange: ((Int) -> Void)?

    init(formatter: NumberFormatter) {
        self.formatter = formatter
        super.init(frame: .zero)
        setupViews()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func willMove(toSuperview newSuperview: UIView?) {
        addTarget(self, action: #selector(editingChanged), for: .editingChanged)
        addTarget(self, action: #selector(resetSelection), for: .allTouchEvents)
        keyboardType = .numberPad
        textAlignment = .left
        sendActions(for: .editingChanged)
    }
    
    override func deleteBackward() {
        text = String(textValue.digits.dropLast())
        sendActions(for: .editingChanged)
    }
    
    private func setupViews() {
        tintColor = .clear
        font = .systemFont(ofSize: 16, weight: .regular)
    }
    
    @objc private func editingChanged() {
        text = currency(from: decimal)
        resetSelection()
        let newValue = Int(doubleValue * 100)
        onValueChange?(newValue) // Notify the parent view
    }
    
    @objc private func resetSelection() {
        selectedTextRange = textRange(from: endOfDocument, to: endOfDocument)
    }
    
    private var textValue: String {
        return text ?? ""
    }
    
    private var doubleValue: Double {
        return (decimal as NSDecimalNumber).doubleValue
    }
    
    private var decimal: Decimal {
        return textValue.decimal
    }
    
    private func currency(from decimal: Decimal) -> String {
        return formatter.string(for: decimal) ?? ""
    }
}

extension StringProtocol where Self: RangeReplaceableCollection {
    var digits: Self { filter(\.isWholeNumber) }
}

extension String {
    var decimal: Decimal { Decimal(string: digits) ?? 0 }
}
