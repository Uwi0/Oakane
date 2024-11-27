import SwiftUI

struct PopUpDialog<Content: View>: View {
    
    let content: Content
    let dismissable: Bool
    let onDimiss: (Bool) -> Void
    
    var body: some View {
        GeometryReader { proxy in
            ZStack {
                Rectangle()
                    .fill(.black.opacity(0.7))
                    .onTapGesture {
                        if dismissable {
                            withAnimation {
                                onDimiss(false)
                            }
                        }
                    }
                VStack {
                    content
                        .frame(maxWidth: proxy.size.width * 0.8)
                }
                .padding(24)
                .background(
                    RoundedRectangle(cornerRadius: 28)
                        .fill(ColorTheme.surfaceContainerHigh)
                        .shadow(radius: 1)
                )
            }
            .ignoresSafeArea(.all)
            .frame(
                width: proxy.size.width,
                height: proxy.size.height,
                alignment: .center
            )
        }
    }
}

extension PopUpDialog {
    
    init(
        dismissable: Bool = true,
        onDismiss: @escaping (Bool) -> Void = {_ in },
        @ViewBuilder _ content: () -> Content
    ) {
        self.content = content()
        self.dismissable = dismissable
        self.onDimiss = onDismiss
    }
}
