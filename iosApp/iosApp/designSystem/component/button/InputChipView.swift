import SwiftUI

struct InputChipView: View {
    let title: String
    let isSelected: Bool
    let trailingIcon: String
    let onSelected: () -> Void
    
    init(title: String, isSelected: Bool, trailingIcon: String, onSelected: @escaping () -> Void) {
        self.title = title
        self.isSelected = isSelected
        self.trailingIcon = trailingIcon
        self.onSelected = onSelected
    }
    
    init (title: String, isSelected: Bool, onSelected: @escaping () -> Void) {
        self.title = title
        self.isSelected = isSelected
        self.trailingIcon = ""
        self.onSelected = onSelected
    }
    
    private var backgroundColor: Color {
        isSelected ? ColorTheme.secondaryContainer : ColorTheme.surfaceContainerLow
    }
    
    private var textColor: Color {
        isSelected ? ColorTheme.onSurfaceVariant : ColorTheme.onSecondaryContainer
    }
    
    private let radius: CGFloat = 12
    
    var body: some View {
        Button(action: onSelected) {
            HStack(alignment: .center, spacing: 8) {
                Text(title)
                    .font(Typography.labelSmall)
                    .foregroundStyle(textColor)
                if !trailingIcon.isEmpty {
                    Image(systemName: trailingIcon)
                        .foregroundStyle(textColor)
                }
            }
            .padding(.horizontal, 12)
            .padding(.vertical, 8)
            .background(backgroundColor)
            .cornerRadius(radius)
            .overlay {
                RoundedRectangle(cornerRadius: radius).stroke(ColorTheme.outlineVariant, lineWidth: 2)
            }
        }
    }
}

#Preview {
    VStack {
        InputChipView(title: "hello", isSelected: false, trailingIcon: "chevron.down", onSelected: {})
        InputChipView(title: "hello", isSelected: true, trailingIcon: "", onSelected: {})
    }
    
}
