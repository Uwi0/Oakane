import SwiftUI

struct OutlinedTextFieldModifier: ViewModifier {
    let borderColor: Color
    let cornerRadius: CGFloat
    let strokeWidth: CGFloat
    
    init(
        borderColor: Color,
        cornerRadius: CGFloat = 12,
        strokeWidth: CGFloat = 2
    ) {
        self.borderColor = borderColor
        self.cornerRadius = cornerRadius
        self.strokeWidth = strokeWidth
    }
    
    func body(content: Content) -> some View {
        content
            .padding(.horizontal, 16)
            .padding(.vertical, 12)
            .frame(maxHeight: 52)
            .overlay(RoundedRectangle(cornerRadius: cornerRadius).stroke(borderColor, lineWidth: strokeWidth))
    }
    
}

extension View {
    func outlinedTextStyle(
        borderColor: Color,
        cornerRadius: CGFloat = 12,
        strokeWidth: CGFloat = 2
    ) -> some View {
        modifier(OutlinedTextFieldModifier(borderColor: borderColor, cornerRadius: cornerRadius, strokeWidth: strokeWidth))
    }
}
