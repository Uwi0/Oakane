import SwiftUI

struct CheckboxToggleStyle: ToggleStyle {
    func makeBody(configuration: Configuration) -> some View {
        HStack {
            ZStack {
                Image(systemName: "square")
                    .foregroundStyle(Color.onSurfaceVariant)
                    .opacity(configuration.isOn ? 0 : 1)
                
                Image(systemName: "checkmark.square.fill")
                    .foregroundStyle(ColorTheme.primary)
                    .opacity(configuration.isOn ? 1 : 0)
                    .scaleEffect(configuration.isOn ? 1 : 0.8)
            }
            .font(.system(size: 20, weight: .bold))
            .animation(.spring(response: 0.2), value: configuration.isOn)
            .onTapGesture {
                withAnimation {
                    configuration.isOn.toggle()
                }
            }
            
            configuration.label
        }
    }
}
