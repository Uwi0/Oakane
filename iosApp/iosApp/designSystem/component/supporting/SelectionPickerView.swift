import SwiftUI

struct SelectionPickerView: View {
    let title: String
    let options: [String]
    @Binding var selectedOpion: String
    
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
    }
}

#Preview {
    SelectionPickerView(title: "Select", options: ["1", "2", "3"], selectedOpion: .constant("1"))
}
