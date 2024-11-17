import SwiftUI
import Shared


struct SelectionPickerView: View {
    let title: String
    let onClick: (String) -> Void
    
    private let options: [String] = TransactionType.allCases.map(\.self.name)
    @State private var selectedOpion: String
    
    
    init(title: String, onClick: @escaping (String) -> Void) {
        self.title = title
        self.onClick = onClick
        self.selectedOpion = options.first ?? ""
    }
    
    var body: some View {
        HStack(alignment: .center) {
            Text(title)
                .font(Typography.bodyLarge)
                .foregroundStyle(ColorTheme.outline)
            Spacer()
            Picker(title, selection: $selectedOpion) {
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
        .onChange(of: selectedOpion) { newValue in
            onClick(newValue)
        }
    }
}

#Preview {
    SelectionPickerView(title: "Select", onClick: { _ in })
}
