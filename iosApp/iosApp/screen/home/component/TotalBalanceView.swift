import SwiftUI

struct TotalBalanceView: View {
    var body: some View {
            VStack(alignment: .center, spacing: 12) {
                Text("Your Balance")
                    .frame(maxWidth: .infinity, alignment: .center)
                Text("Rp. 0")
                    .font(.title2)
                    .frame(maxWidth: .infinity, alignment: .center)
            }
            .padding(12)
            .background(
                RoundedRectangle(cornerRadius: ShapeStyles.large)
                    .fill(surface)
                    .shadow(radius: 4)
            )
        }
}

#Preview {
    TotalBalanceView()
}
