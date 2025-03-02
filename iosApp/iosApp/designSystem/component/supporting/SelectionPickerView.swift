import SwiftUI
import Shared


struct SelectionPickerView: View {
    let title: String
    var label: String = ""
    var options: [String] = TransactionType.allCases.map(\.self.name)
    @Binding var selectedOption: String
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            if !label.isEmpty {
                Text(label).font(Typography.titleSmall)
            }
            SelectionPickerContent()
        }
    }
    
    @ViewBuilder
    private func SelectionPickerContent() -> some View {
        HStack(alignment: .center) {
            Text(title)
                .font(Typography.bodyLarge)
                .foregroundStyle(ColorTheme.outline)
            Spacer()
            PickersContent()
        }
        .outlinedTextStyle(borderColor: ColorTheme.outline)
    }
    
    @ViewBuilder
    private func PickersContent() -> some View {
        Picker(title, selection: $selectedOption) {
            ForEach(options, id: \.self) { option in
                Text(option).tag(option)
            }
        }
        .pickerStyle(.menu)
        .tint(ColorTheme.outline)
    }
}

#Preview {
    SelectionPickerView(title: "Select",selectedOption: .constant(""))
}
