import SwiftUI

struct CustomBackgroundModifier: ViewModifier {
    
    var cornerRadius: CGFloat
    var backgroundColor: Color
    var shadowRadius: CGFloat
    
    func body(content: Content) -> some View {
        content
            .padding(.vertical, 12)
            .padding(.horizontal, 16)
            .background(
                RoundedRectangle(cornerRadius: cornerRadius)
                    .fill(backgroundColor)
                    .shadow(radius: shadowRadius)
            )
    }
}

extension View {
    func customBackground(
        cornerRadius: CGFloat = ShapeStyles.large,
        backgroundColor: Color,
        shadowRadius: CGFloat = 1
    ) -> some View {
        self.modifier(
            CustomBackgroundModifier(
                cornerRadius: cornerRadius,
                backgroundColor: backgroundColor,
                shadowRadius: shadowRadius
            )
        )
    }
}

