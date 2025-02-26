import SwiftUI

struct HorizontalColorSelectorView: View {
    
    let colors: [Color]
    @Binding var selectedColor: Color
    
    var body: some View {
        ScrollView(.horizontal) {
            HStack(spacing: 16) {
                CategoryIconView(
                    icon: IconConstant.Paintbrush,
                    color: selectedColor
                )
                .allowsHitTesting(false)
                .background {
                    ColorPicker("CustomColor", selection: $selectedColor)
                        .labelsHidden()
                }
                ForEach(colors, id: \.self) { color in
                    Circle()
                        .fill(color)
                        .frame(width: 48, height: 48)
                        .onTapGesture {
                            selectedColor = color
                        }
                }
            }
        }
        .scrollIndicators(.hidden)
    }
}
