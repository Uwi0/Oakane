import SwiftUI

struct ToolbarModifier: ViewModifier {
    let onAction: (ToolbarEvent) -> Void
    func body(content: Content) -> some View {
        content
            .toolbar {
                ToolbarItem(placement: .topBarLeading) {
                    
                }
            }
    }
}

struct ToolBarContent {
    let title: String
    var action1: String = ""
    var action2: String = ""
}

enum ToolbarEvent {
    case dismiss
    case action1
    case action2
}
