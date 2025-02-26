import SwiftUI

struct CardNoteView: View {
    
    let note: String
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Note")
                .font(Typography.titleMedium)
            
            Text(note)
                .font(Typography.bodyMedium)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}

#Preview {
    let note: String = "Hello, World!"
    CardNoteView(note: note)
}
