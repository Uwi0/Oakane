import SwiftUI

struct HorizontalColorSelectorView: View {
    
    let colors: [String]
    let onSelectedColor: (String) -> Void
    @State private var selectedColor: Color
    
    init(selectedColor: Color, colors: [String], onSelectedColor: @escaping (String) -> Void) {
        self.colors = colors
        self.onSelectedColor = onSelectedColor
        self.selectedColor = selectedColor
    }
    
    var body: some View {
        ScrollView(.horizontal) {
            HStack(spacing: 16) {
                CategoryIconView(
                    icon: IconConstant.Paintbrush,
                    color: selectedColor
                )
                .allowsHitTesting(false)
                .background {
                    ColorPicker("CustomColor",selection: $selectedColor)
                        .labelsHidden()
                        .onChange(of: selectedColor) {
                            let colorHex = selectedColor.toHexString() ?? "0xFFFFFF"
                            onSelectedColor(colorHex)
                        }
                }
                ForEach(colors, id: \.self) { hex in
                    let colorHex = hex.toColorLong()
                    let color = Color(hex: colorHex)
                    Circle()
                        .fill(color)
                        .frame(width: 48, height: 48)
                        .onTapGesture {
                            selectedColor = color
                            onSelectedColor(hex)
                        }
                }
            }
        }
        .scrollIndicators(.hidden)
    }
}
