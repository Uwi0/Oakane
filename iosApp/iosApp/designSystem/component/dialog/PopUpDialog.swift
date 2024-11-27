import SwiftUI

struct PopUpDialog<Content: View>: View {
    
    @Binding var isPresented: Bool
    let content: Content
    let dismissable: Bool
    
    var body: some View {
        GeometryReader { proxy in
            ZStack {
                Rectangle()
                    .fill(.black.opacity(0.7))
                    .onTapGesture {
                        if dismissable {
                            withAnimation {
                                isPresented = false
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
        isPresented: Binding<Bool>,
        dismissable: Bool = true,
        @ViewBuilder _ content: () -> Content
    ) {
        _isPresented = isPresented
        self.content = content()
        self.dismissable = dismissable
    }
}
