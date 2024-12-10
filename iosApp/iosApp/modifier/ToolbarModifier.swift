import SwiftUI

struct ToolbarModifier: ViewModifier {
    let toolbarContent: ToolBarContent
    let onAction: (ToolbarEvent) -> Void
    func body(content: Content) -> some View {
        content
            .toolbar {
                ToolbarItem(placement: .topBarLeading) {
                    IconButtonView(name: "arrow.left", width: 16, onClick: { onAction(.dismiss) })
                }
                ToolbarItem(placement: .topBarLeading) {
                    Text(toolbarContent.title)
                        .font(Typography.titleMedium)
                }
                ToolbarItem(placement: .topBarTrailing){
                    HStack(spacing: 16) {
                        if !toolbarContent.action1.isEmpty {
                            IconButtonView(name: toolbarContent.action1, width: 16, onClick: { onAction(.action1)})
                        }
                        if !toolbarContent.action2.isEmpty {
                            IconButtonView(name: toolbarContent.action2, width: 16, onClick: { onAction(.action2)})
                        }
                    }
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

extension View {
    func customToolbar(content: ToolBarContent, onEvent: @escaping (ToolbarEvent) -> Void) -> some View {
        self.modifier(ToolbarModifier(toolbarContent: content, onAction: onEvent))
    }
}
