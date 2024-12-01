import SwiftUI

struct NavigationTopAppbar<Content: View>: View {
    
    let title: String
    let actionContent: Content
    let onNavigateBack: () -> Void
    
    var body: some View {
        HStack(spacing: 16) {
            Image(systemName: "arrow.left")
                .fontWeight(.semibold)
                .frame(width: 24, height: 24)
                .onTapGesture {
                    onNavigateBack()
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
        @ViewBuilder actionContent: @escaping () -> Content = { EmptyView() },
        navigateBack: @escaping () -> Void
    ) {
        self.title = title
        self.actionContent = actionContent()
        self.onNavigateBack = navigateBack
    }
}

#Preview {
    VStack {
        NavigationTopAppbar(title: "Title1", navigateBack: {})
        NavigationTopAppbar(
            title: "title2",
            actionContent: {
                Image(systemName: "eraser")
                    .frame(width: 24, height: 24)
                Image(systemName: "pencil")
                    .frame(width: 24, height: 24)
            },
            navigateBack: {}
        )
    }
    
}
