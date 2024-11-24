import SwiftUI

struct OutlinedTextFieldView: View {
    
    private let label: String
    private let placeHolder: String
    private let showLabel: Bool
    private let onValueChange: (String) -> Void

    @State private var value: String = ""
    @State private var borderColor = ColorTheme.outline
    
    init(
        value: String,
        placeHolder: String = "PlaceHolder",
        label: String = "",
        showLabel: Bool = true,
        onValueChange: @escaping (String) -> Void
    ){
        self.value = value
        self.placeHolder = placeHolder
        self.label = label.isEmpty ? placeHolder : label
        self.showLabel = showLabel
        self.onValueChange = onValueChange
    }
    
    var body: some View {
        VStack(alignment: .leading,spacing: 8) {
            if showLabel {
                Text(label)
                    .font(Typography.titleSmall)
            }
            
            TextField(placeHolder, text: $value, onEditingChanged: { isEditChange in
                borderColor = isEditChange ? ColorTheme.primary : ColorTheme.outline
            })
                .tint(ColorTheme.onSurfaceVariant)
                .autocorrectionDisabled(true)
                .padding(12)
                .overlay(RoundedRectangle(cornerRadius: 16)
                .stroke(borderColor, lineWidth: 2))
        }
        .onChange(of: value){ newValue in
            onValueChange(newValue)
        }
    }
}

#Preview {
    OutlinedTextFieldView(value: "", placeHolder: "PlaceHolder", onValueChange: {_ in })
}
