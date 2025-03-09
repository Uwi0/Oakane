import SwiftUI

struct NavigationTopAppbar<Content: View>: View {
    
    let title: String
    let actionContent: Content
    var showDrawer: Bool
    let onAction: () -> Void
    
    var body: some View {
        HStack(spacing: 16) {
            Image(systemName: showDrawer ? "line.3.horizontal" :"arrow.left")
                .resizable()
                .scaledToFit()
                .frame(width: 24, height: 24)
                .onTapGesture {
                    onAction()
                }
            Text(title)
                .font(Typography.titleLarge)
            Spacer()
            HStack {
                actionContent
                    .fontWeight(.semibold)
            }
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 8)
    }
}

extension NavigationTopAppbar {
    
    init(
        title: String,
        showDrawer: Bool = false,
        @ViewBuilder actionContent: @escaping () -> Content = { EmptyView() },
        onAction: @escaping () -> Void
    ) {
        self.title = title
        self.actionContent = actionContent()
        self.onAction = onAction
        self.showDrawer = showDrawer
    }
}

#Preview {
    VStack {
        NavigationTopAppbar(title: "Title1", onAction: {})
        NavigationTopAppbar(
            title: "title2",
            actionContent: {
                Image(systemName: "eraser")
                    .frame(width: 24, height: 24)
                Image(systemName: "pencil")
                    .frame(width: 24, height: 24)
            },
            onAction: {}
        )
    }
    
}
