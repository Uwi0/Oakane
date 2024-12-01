import SwiftUI

struct GoalsScreen: View {
    var body: some View {
        GeometryReader { proxy in
            ColorTheme.surface.ignoresSafeArea()
            VStack{
                Text("Hello world")
                Spacer()
                VStack {
                    Text("Another hellow world")
                }
                .padding(.horizontal, 16)
                .padding(.vertical, 24)
                Spacer()
            }
            .frame(width: proxy.size.width, height: proxy.size.height)
            
        }
        
    }
}

#Preview {
    GoalsScreen()
}
