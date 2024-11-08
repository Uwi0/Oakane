import SwiftUI

struct OutlinedButtonView: View {
    
    let text: String
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            Text(text)
                .foregroundStyle(ColorTheme.primary)
                .padding(.horizontal, 24)
                .padding(.vertical, 16)
        }
        .overlay {
            RoundedRectangle(cornerRadius: 16)
                .stroke(ColorTheme.primary, lineWidth: 3)
        }
    }
}

#Preview {
    OutlinedButtonView(text: "Hello", onClick: {})
}
