import SwiftUI

struct FilterSelectorView: View {
    var body: some View {
        ScrollView(.horizontal) {
            HStack(spacing: 8) {
                ChipSelectorView(name: "By Type", isSelected: true, onClick: {})
                ChipSelectorView(name: "By Date", isSelected: false, onClick: {})
                ChipSelectorView(name: "By Category", isSelected: false, onClick: {})
            }
        }
    }
}

private struct ChipSelectorView: View {
    let name: String
    let isSelected: Bool
    let onClick: () -> Void
    
    private var icon: String {
        isSelected ? "xmark" : "chevron.down"
    }
    
    var body: some View {
        InputChipView(title: name, isSelected: isSelected, trailingIcon: icon, onSelected: onClick)
    }
}

#Preview {
    FilterSelectorView()
}
