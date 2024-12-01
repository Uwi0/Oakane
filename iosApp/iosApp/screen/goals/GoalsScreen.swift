import SwiftUI

struct GoalsScreen: View {
    var body: some View {
        GeometryReader { proxy in
            ColorTheme.surface.ignoresSafeArea()
            VStack{
                GoalsTopAppBar()
                ScrollView {
                    VStack(spacing: 16) {
                        Text("Another hellow world")
                    }
                }
                .scrollIndicators(.hidden)
                .padding(.horizontal, 16)
                .padding(.vertical, 24)
            }
            .frame(width: proxy.size.width, height: proxy.size.height)
            
            FabButtonView(
                size: FabConstant.size,
                xPos: proxy.size.width - FabConstant.xOffset,
                yPos: proxy.size.height - FabConstant.yOffset,
                onClick: {}
            )
        }
        
    }
}

#Preview {
    GoalsScreen()
}
