import SwiftUI

struct TotalBalanceView: View {
    var body: some View {
            VStack(alignment: .center, spacing: 12) {
                Text("Your Balance")
                    .font(Typography.bodyLarge)
                    .frame(maxWidth: .infinity, alignment: .center)
                Text("Rp. 0")
                    .font(Typography.titleMedium)
                    .frame(maxWidth: .infinity, alignment: .center)
            }
            .customBackground(backgroundColor: ColorTheme.surface)
        }
}

#Preview {
    TotalBalanceView().padding()
}
