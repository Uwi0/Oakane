import SwiftUI

struct FilledButtonView: View {
    
    let text: String
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            Text(text)
                .font(.headline)
                .foregroundColor(ColorTheme.onPrimary)
                .frame(maxWidth: .infinity, maxHeight: 48)
                .padding()
                .background(ColorTheme.primary)
                .cornerRadius(16)
        }
    }
}

#Preview {
    FilledButtonView(text: "button", onClick: {})
}
