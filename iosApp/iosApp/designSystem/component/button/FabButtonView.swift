import SwiftUI

struct FabConstant {
    static let xOffset: CGFloat = 35
    static let yOffset: CGFloat = 35
}


struct FabButtonView: View {
    
    let size: CGFloat
    let xPos: CGFloat
    let yPos: CGFloat
    let onClick: () -> Void
    
    var body: some View {
        
        Button(
            action: onClick,
            label: {
                Image(systemName: "plus")
                    .resizable()
                    .scaledToFit()
                    .foregroundStyle(ColorTheme.onPrimaryContainer)
                    .padding()
                    .background(ColorTheme.primaryContainer)
                    .clipShape(RoundedRectangle(cornerRadius: 16.0))
                    .shadow(radius: 16)
            }
        )
        .frame(width: size, height: size)
        .padding()
        .position(x: xPos, y: yPos)
        
    }
}

#Preview {
    FabButtonView(size: 56, xPos: 10, yPos: 10, onClick: {})
}
