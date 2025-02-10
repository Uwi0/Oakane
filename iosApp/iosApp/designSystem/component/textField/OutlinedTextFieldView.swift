import SwiftUI

struct OutlinedTextFieldView: View {
    
    private let label: String
    private let placeHolder: String
    private let showLabel: Bool
    
    @Binding var value: String
    @State private var borderColor = ColorTheme.outline
    
    init(
        value: Binding<String>,
        placeHolder: String = "PlaceHolder",
        label: String = "",
        showLabel: Bool = true
    ) {
        self._value = value
        self.placeHolder = placeHolder
        self.label = label.isEmpty ? placeHolder : label
        self.showLabel = showLabel
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            if showLabel {
                Text(label)
                    .font(Typography.titleMedium)
            }
            
            TextField(placeHolder, text: $value, onEditingChanged: { isEditChange in
                borderColor = isEditChange ? ColorTheme.primary : ColorTheme.outline
            })
            .tint(ColorTheme.onSurfaceVariant)
            .autocorrectionDisabled(true)
            .outlinedTextStyle(borderColor: borderColor)
        }
    }
}
#Preview {
    OutlinedTextFieldView(
        value: .constant("Hello world"),
        placeHolder: "PlaceHolder"
    )
}
