import SwiftUI

struct FilterSelectorView: View {
    let selectedType: String
    let selectedDate: Int64
    let selectedCategory: String
    let onClick: (TransactionsUiEvent) -> Void
    var body: some View {
        ScrollView(.horizontal) {
            HStack(spacing: 8) {
                ChipSelectorView(
                    name: "By Type",
                    isSelected: !selectedType.isEmpty,
                    onClick: { onClick(TransactionsUiEvent.TransactionType)}
                )
                ChipSelectorView(
                    name: "By Date",
                    isSelected: selectedDate != 0,
                    onClick: {onClick(TransactionsUiEvent.DateCreated)}
                )
                ChipSelectorView(
                    name: "By Category",
                    isSelected: !selectedCategory.isEmpty,
                    onClick: {onClick(TransactionsUiEvent.Categroy)}
                )
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
    FilterSelectorView(
        selectedType: "InCome",
        selectedDate: 0,
        selectedCategory: "",
        onClick: {_ in }
    )
}
