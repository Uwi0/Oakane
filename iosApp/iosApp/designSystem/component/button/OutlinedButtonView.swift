import SwiftUI

struct OutlinedButtonView: View {
    
    let text: String
    let onClick: () -> Void
    var color: Color = ColorTheme.primary
    
    var body: some View {
        Button(action: onClick) {
            Text(text)
                .foregroundStyle(color)
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .padding(.horizontal, 24)
                .padding(.vertical, 16)
        }
        .overlay {
            RoundedRectangle(cornerRadius: 16)
                .stroke(color, lineWidth: 2)
        }
    }
}

#Preview {
    OutlinedButtonView(text: "Hello", onClick: {})
}
