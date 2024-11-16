import SwiftUI

struct SelectionPickerView: View {
    let title: String
    let options: [String]
    let onClick: (String) -> Void
    @State private var selectedOpion: String
    
    init(title: String, options: [String], onClick: @escaping (String) -> Void) {
        self.title = title
        self.options = options
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
    SelectionPickerView(title: "Select", options: ["1", "2", "3"], onClick: { _ in })
}
