import SwiftUI

struct DyhamicHeightSheetModifier<V>: ViewModifier where V: View {
    @Binding var isPresented: Bool
    @State private var sheetHeight: CGFloat = .zero
    let myContent: () -> V
    
    func body(content viewContent: Content) -> some View {
        viewContent
            .sheet(isPresented: $isPresented) {
                self.myContent()
                    .padding(.horizontal, 16)
                    .padding(.vertical, 8)
                    .overlay(
                        GeometryReader { geometry in
                            Color.clear
                                .preference(key: InnerHeightPreferenceKey.self, value: geometry.size.height)
                        }
                    )
                    .onPreferenceChange(InnerHeightPreferenceKey.self) { newHeight in
                        if sheetHeight != newHeight {
                            sheetHeight = newHeight
                        }
                    }
                    .presentationDetents([.height(sheetHeight)])
                    .id(sheetHeight)
            }
    }
}

extension View {
    func dynamicHeightSheet<V: View>(isPresented: Binding<Bool>, @ViewBuilder content: @escaping () -> V) -> some View {
        modifier(DyhamicHeightSheetModifier(isPresented: isPresented, myContent: content))
    }
}

struct InnerHeightPreferenceKey: PreferenceKey {
    static let defaultValue: CGFloat = .zero
    static func reduce(value: inout CGFloat, nextValue: () -> CGFloat) {
        value = nextValue()
    }
}
