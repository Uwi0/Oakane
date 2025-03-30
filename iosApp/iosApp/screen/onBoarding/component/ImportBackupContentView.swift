import SwiftUI
import Shared

struct ImportBackupContentView: View {
    
    let onEvent: (OnBoardingEvent) -> Void
    @State private var isPresented: Bool = false
    
    var body: some View {
        VStack(alignment: . center) {
            Text("Import Your Backup")
                .font(Typography.displaySmall)
            Spacer().frame(height: 148)
            Image(ImageConstants.oakaneEmpty)
                .resizable()
                .frame(width: 236, height: 236)
            Spacer().frame(height: 16)
            Text("You can import your back up to restore your data,  if you have one from previous back up or previous device.")
                .font(Typography.bodyMedium)
            Spacer()
            ButtonBackup(onClick: { isPresented = true})
            Spacer().frame(height: 16)
            ButtonStartFresh(onClick: { onEvent(.NavigateNext(content: .selectCurrency))})
            
        }
        .padding(.vertical, 24)
        .padding(.horizontal, 16)
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
        .background(ColorTheme.surface.ignoresSafeArea())
        .fileImporter(
            isPresented: $isPresented,
            allowedContentTypes: [.json],
            onCompletion: { result in
                switch result{
                case .success(let url): readBackupFile(url: url)
                case .failure(let error): print("Error: \(error)")
                }
            }
        )
    }
    
    @ViewBuilder private func ButtonBackup(onClick: @escaping () -> Void) -> some View {
        OutlinedContentButtonView(
            onClick: onClick,
            content: {
                Image(systemName: "square.and.arrow.down").frame(width: 24, height: 24).fontWeight(.bold)
                Spacer()
                Text("Import Backup").font(Typography.titleMedium)
                Spacer()
                Spacer().frame(width: 24)
            }
        )
    }
    
    @ViewBuilder private func ButtonStartFresh(onClick: @escaping () -> Void) -> some View {
        FilledContentButtonView(
            onclick: onClick,
            content: {
                Spacer().frame(width: 24, height: 24)
                Spacer()
                Text("Start Fresh").font(Typography.titleMedium)
                Spacer()
                Image(systemName: "chevron.right").fontWeight(.bold)
            }
        )
        
    }
    
    private func readBackupFile(url: URL) {
        let result = readJSONFileAsString(fileUrl: url)
        switch result {
        case .success(let json): onEvent(.RestoreBackup(json: json))
        case .failure(let error): print("error \(error)")
        }
    }
}

#Preview {
    ImportBackupContentView(onEvent: { _ in })
}
