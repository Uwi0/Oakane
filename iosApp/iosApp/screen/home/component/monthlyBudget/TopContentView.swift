import SwiftUI
import Shared

internal struct TopContentView: View {
    
    let onEvent: (HomeEvent) -> Void
    private let imageSize: CGFloat = 24
    
    var body: some View {
        HStack(alignment: .center, spacing: 16) {
            OutlinedCircleIcon(imageName: "dollarsign", size: imageSize)
            VStack(alignment: .leading,spacing: 8) {
                HStack(alignment: .center) {
                    Text("Montly budget")
                        .font(Typography.titleMedium)
                    Spacer()
                    IconButtonView(
                        name: "pencil",
                        width: imageSize,
                        onClick: { onEvent(.ToMonthlyBudget())},
                        fontWeight: .bold
                    )
                }
                Text("Rp. 0")
                    .font(.title3)
                    .fontWeight(.semibold)
                ProgressIndicatorView(value: 0.5)
                HStack{
                    Text("Spent Rp. 0")
                        .font(Typography.bodyMedium)
                    Spacer()
                    Text("Left Rp. 0")
                        .font(Typography.bodyMedium)
                }
            }
        }
    }
}

#Preview {
    TopContentView(onEvent: { _ in }).padding()
}
