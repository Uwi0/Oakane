import SwiftUI

struct FilledButtonView: View {
    
    let text: String
    var bgColor: Color = ColorTheme.primary
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            Text(text)
                .font(Typography.labelLarge)
                .foregroundColor(ColorTheme.onPrimary)
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .padding()
                .background(bgColor)
                .cornerRadius(16)
        }
    }
}

#Preview {
    FilledButtonView(text: "button", onClick: {})
}
