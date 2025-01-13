import SwiftUI

struct OutlinedCheckmarkRadioButton: View {
    let selected: Bool
    let onClick: () -> Void

    var body: some View {
        Button(action: onClick) {
            ZStack {
                Circle()
                    .stroke(
                        selected ? ColorTheme.primary : ColorTheme.outline,
                        lineWidth: 2
                    )
                    .frame(width: 24, height: 24)

                if selected {
                    Image(systemName: "checkmark")
                        .resizable()
                        .scaledToFit()
                        .foregroundColor(ColorTheme.primary)
                        .frame(width: 12, height: 12)
                }
            }
        }
        .buttonStyle(PlainButtonStyle())
    }
}
