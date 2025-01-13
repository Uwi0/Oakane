import SwiftUI

struct ImportBackupContentView: View {
    var body: some View {
        VStack(alignment: .leading) {
            Text("Import Your Backup")
                .font(Typography.displaySmall)
            Spacer().frame(height: 148)
            Image(ImageConstants.mumeiBackup)
                .frame(width: 236, height: 236)
            Spacer().frame(height: 16)
            Text("You can import your back up to restore your data,  if you have one from previous back up or previous device.")
                .font(Typography.bodyMedium)
            Spacer()
            ButtonSkip(onClick: {})
            Spacer().frame(height: 16)
            ButtonStartFresh(onClick: {})
            
        }
        .padding(.vertical, 24)
        .padding(.horizontal, 16)
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
        .background(ColorTheme.surface.ignoresSafeArea())
    }
    
    @ViewBuilder private func ButtonSkip(onClick: @escaping () -> Void) -> some View {
        Button(action: onClick) {
            HStack {
                Image(systemName: "square.and.arrow.down").frame(width: 24, height: 24).fontWeight(.bold)
                Spacer()
                Text("Import Backup").font(Typography.titleMedium)
                Spacer()
                Spacer().frame(width: 24)
            }
            .foregroundStyle(ColorTheme.primary)
            .padding(.vertical, 16)
            .padding(.horizontal, 16)
            .background(RoundedRectangle(cornerRadius: 16).stroke(ColorTheme.primary, lineWidth: 2))
        }
        .buttonStyle(.plain)
    }
    
    @ViewBuilder private func ButtonStartFresh(onClick: @escaping () -> Void) -> some View {
        Button(action: onClick) {
            HStack {
                Spacer().frame(width: 24, height: 24)
                Spacer()
                Text("Start Fresh").font(Typography.titleMedium)
                Spacer()
                Image(systemName: "chevron.right").frame(width: 24, height: 24).fontWeight(.bold)
            }
            .foregroundStyle(ColorTheme.onPrimary)
            .padding(.vertical, 16)
            .padding(.horizontal, 16)
            .background(RoundedRectangle(cornerRadius: 16).fill(ColorTheme.primary))
        }
    }
}

#Preview {
    ImportBackupContentView()
}
