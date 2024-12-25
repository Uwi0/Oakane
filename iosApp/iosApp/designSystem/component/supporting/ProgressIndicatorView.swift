import SwiftUI

struct ProgressIndicatorView: View {
    @State private var animatedProgress: Float = 0.0
    @State private var isUpdating = false
    var value: Float
    
    private let radius: CGFloat = 16
    
    var body: some View {
        
        ProgressView(value: animatedProgress, total: 1.0)
            .progressViewStyle(LinearProgressViewStyle())
            .tint(ColorTheme.primary)
            .cornerRadius(radius)
            .scaleEffect(x: 1, y: 2, anchor: .center)
            .onAppear {
                withAnimation(.easeInOut(duration: 0.5)) {
                    animatedProgress = value
                }
            }
            .onChange(of: value) {
                guard !isUpdating else { return }
                isUpdating = true
                withAnimation(.easeInOut(duration: 0.5)) {
                    animatedProgress = value
                }
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                    isUpdating = false
                }
            }
    }
}

#Preview {
    ProgressIndicatorView(value: 0.5).padding()
}
