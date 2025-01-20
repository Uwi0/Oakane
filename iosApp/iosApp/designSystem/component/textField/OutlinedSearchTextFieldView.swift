import SwiftUI

struct OutlinedSearchTextFieldView: View {
    @Binding var query: String
    let placeHolder: String
    
    @State private var borderColor = ColorTheme.outline
    @StateObject private var observer: DebouncedQueryObserver = DebouncedQueryObserver()

    var body: some View {
        HStack {
            Image(systemName: "magnifyingglass")
                .resizable()
                .frame(width: 24, height: 24)
                .foregroundColor(ColorTheme.outline)
            
            TextField(placeHolder, text: $observer.searchQuery, onEditingChanged: { isEditChange in
                borderColor = isEditChange ? ColorTheme.primary : ColorTheme.outline
            })
                .tint(ColorTheme.onSurfaceVariant)
                .autocorrectionDisabled(true)
            if !query.isEmpty {
                Button(
                    action: { observer.searchQuery = ""},
                    label: {
                        Image(systemName: "xmark.circle.fill")
                            .foregroundStyle(ColorTheme.outline)
                    }
                )
            }
        }
        .outlinedTextStyle(borderColor: borderColor)
        .onReceive(observer.$debouncedQuery) { val in
            query = val
        }
    }
}

#Preview {
    OutlinedSearchTextFieldView(query: .constant(""), placeHolder: "")
}
