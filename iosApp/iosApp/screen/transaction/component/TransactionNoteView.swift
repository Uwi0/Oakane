import SwiftUI

struct TransactionNoteView: View {
    let note: String
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text("Note")
                .font(Typography.titleSmall)
                .foregroundStyle(ColorTheme.primary)
            
            Text(note)
                .font(Typography.bodyMedium)
                .foregroundStyle(ColorTheme.outline)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}

#Preview {
    TransactionNoteView(note: "A string is a series of characters, such as Swift, that forms a collection. Strings in Swift are Unicode correct and locale insensitive, and are designed to be efficient. The String type bridges with the Objective-C class NSString and offers interoperability with C functions that works with strings")
}
