import SwiftUI
import Shared


struct SelectionPickerView: View {
    let title: String
    @Binding var selectedOption: String
    let onClick: (String) -> Void
    
    private let options: [String] = TransactionType.allCases.map(\.self.name)
    
    var body: some View {
        HStack(alignment: .center) {
            Text(title)
                .font(Typography.bodyLarge)
                .foregroundStyle(ColorTheme.outline)
            Spacer()
            Picker(title, selection: $selectedOption) {
                ForEach(options, id: \.self) { option in
                    Text(option)
                        .tag(option)
                }
            }
            .pickerStyle(.menu)
            .tint(ColorTheme.outline)
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 8)
        .background(RoundedRectangle(cornerRadius: 16).stroke(ColorTheme.outline, lineWidth: 2))
        .onChange(of: selectedOption) {
            onClick(selectedOption)
        }
    }
}

#Preview {
    SelectionPickerView(title: "Select",selectedOption: .constant("") ,onClick: { _ in })
}
