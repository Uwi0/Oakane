import SwiftUI

internal struct TopContentView: View {
    
    private let imageSize: CGFloat = 24
    
    var body: some View {
        HStack(alignment: .center, spacing: 16) {
            OutlinedCircleIcon(imageName: "dollarsign", size: imageSize)
            VStack(alignment: .leading,spacing: 8) {
                HStack(alignment: .center) {
                    Text("Montly budget")
                        .font(.title2)
                    Spacer()
                    IconButtonView(
                        name: "pencil",
                        size: imageSize,
                        onClick: {}
                    )
                }
                Text("Rp. 0")
                    .font(.title3)
                    .fontWeight(.semibold)
                ProgressIndicatorView(value: 0.5)
                HStack{
                    Text("Spent Rp. 0")
                        .font(TypoGraphy.bodyMedium)
                    Spacer()
                    Text("Left Rp. 0")
                        .font(TypoGraphy.bodyMedium)
                }
            }
        }
    }
}

#Preview {
    TopContentView().padding()
}
