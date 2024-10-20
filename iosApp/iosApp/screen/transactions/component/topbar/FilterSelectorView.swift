import SwiftUI

struct FilterSelectorView: View {
    @Binding var selectedType: String
    @Binding var selectedDate: Int64
    let selectedCategory: String
    let onClick: (TransactionsUiEvent) -> Void
    var body: some View {
        ScrollView(.horizontal) {
            HStack(spacing: 8) {
                ChipSelectorView(
                    name: "By Type",
                    isSelected: !selectedType.isEmpty,
                    onClick: filterByType
                )
                ChipSelectorView(
                    name: "By Date",
                    isSelected: selectedDate != 0,
                    onClick: filterByDate
                )
                ChipSelectorView(
                    name: "By Category",
                    isSelected: !selectedCategory.isEmpty,
                    onClick: {onClick(TransactionsUiEvent.Categroy)}
                )
            }
        }
    }
    
    func filterByType(){
        if selectedType.isEmpty {
            onClick(TransactionsUiEvent.TransactionType)
        } else {
            selectedType = ""
        }
    }
    
    func filterByDate(){
        if selectedDate == 0 {
            onClick(TransactionsUiEvent.DateCreated)
        } else {
            selectedDate = 0
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
        selectedType: .constant("InCome"),
        selectedDate: .constant(0),
        selectedCategory: "",
        onClick: {_ in }
    )
}
