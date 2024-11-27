import SwiftUI

struct ProgressIndicatorView: View {
    @State private var animatedProgress: Float = 0.0
    var value: Float
    
    private let radius: CGFloat = 12
    
    var body: some View {
        
        ProgressView(value: animatedProgress, total: 1.0)
            .progressViewStyle(LinearProgressViewStyle())
            .controlSize(.large)
            .tint(ColorTheme.primary)
            .cornerRadius(radius)
            .onAppear {
                withAnimation(.easeInOut(duration: 0.5)) {
                    animatedProgress = value
                }
            }
            .onChange(of: value) { newValue in
                withAnimation(.easeInOut(duration: 0.5)) {
                    animatedProgress = newValue
                }
            }
    }
}

#Preview {
    ProgressIndicatorView(value: 0.5).padding()
}
