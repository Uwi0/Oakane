import SwiftUI

struct OutlinedCircleIcon: View {
    let imageName: String
    let size: CGFloat
    var body: some View {
        Image(systemName: imageName)
            .resizable()
            .scaledToFit()
            .frame(width: size, height: size)
            .padding()
            .background(Circle().stroke(primary, lineWidth: 3))
    }
}


#Preview {
    OutlinedCircleIcon(imageName: "dollarsign", size: 64)
}
