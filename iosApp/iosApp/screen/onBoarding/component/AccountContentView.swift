import SwiftUI

struct AccountContentView: View {
    var body: some View {
        VStack(alignment: .leading) {
            Text("Oakane")
                .font(Typography.displayMedium)
            Spacer().frame(height: 8)
            Text("Your personal finance manager")
                .font(Typography.labelLarge)
            Text("#Open source")
                .font(Typography.labelSmall)
                .foregroundStyle(ColorTheme.primary)
            Spacer().frame(height: 164)
            Text("Your data will be stored locally on your device. if you uninstall the app or switch devices. you may lose your data. To prevent this, we recommend that you regularly export your backups.")
                .font(Typography.bodySmall)
            Spacer().frame(height: 16)
            ButtonAccount(onClick: {})
            Spacer()
            Text("By signing in. you accept our Terms & Conditions and privacy policy")
                .multilineTextAlignment(.center)
                .frame(maxWidth: .infinity, alignment: .center)
        }
        .padding(.vertical, 24)
        .padding(.horizontal, 16)
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
        .background(ColorTheme.surface.ignoresSafeArea())

    }
    
    @ViewBuilder private func ButtonAccount(onClick: @escaping () -> Void) -> some View {
        Button(action: onClick) {
            HStack(spacing: 16) {
                Image(systemName: "person.crop.circle")
                    .resizable()
                    .frame(width: 24, height: 24)
                    .foregroundStyle(ColorTheme.onPrimary)
                
                Text("Offline Account")
                    .font(Typography.titleMedium)
                    .foregroundStyle(ColorTheme.onPrimary)
                Spacer()
            }
            .padding(.vertical, 12)
            .padding(.horizontal, 16)
            .frame(maxWidth: .infinity)
            .background(
                RoundedRectangle(cornerRadius: 8)
                    .fill(ColorTheme.primary)
            )
        }
    }
}

#Preview {
    AccountContentView()
}
