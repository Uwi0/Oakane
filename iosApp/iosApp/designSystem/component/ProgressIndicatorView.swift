import SwiftUI

struct ProgressIndicatorView: View {
    @State private var animatedProgress: Float = 0.0
    var value: Float
    
    private let thickness: CGFloat = 12
    
    var body: some View {
        ProgressView(value: animatedProgress, total: 1.0)
            .progressViewStyle(LinearProgressViewStyle())
            .tint(Oakane.primary)
            .frame(width: .infinity,height: thickness)
            .cornerRadius(thickness)
            .onAppear {
                withAnimation(.easeInOut(duration: 0.5)) {
                    animatedProgress = value
                }
            }
    }
}

#Preview {
    ProgressIndicatorView(value: 0.5).padding()
}
