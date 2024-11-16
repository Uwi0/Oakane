import SwiftUI
import Shared

struct FilterSelectorView: View {
    let uiState: TransactionsState
    let onEvent: (TransactionsEvent) -> Void
    
    var body: some View {
        ScrollView(.horizontal) {
            HStack(spacing: 8) {
                ChipSelectorView(
                    name: "By Type",
                    isSelected: uiState.selectedType != nil,
                    onClick: onSelectedType
                )
                ChipSelectorView(
                    name: "By Date",
                    isSelected: uiState.selectedDate != 0,
                    onClick: onSelectedDate
                )
                ChipSelectorView(
                    name: "By Category",
                    isSelected: uiState.selectedCategory != nil,
                    onClick: onSelectedCategory
                )
            }
        }
    }
    
    private func onSelectedType() {
        if uiState.selectedType == nil {
            onEvent(.ShowSheet(content: .type))
        } else {
            onEvent(.FilterByType(value: nil))
        }
    }
    
    private func onSelectedDate() {
        if uiState.selectedDate == 0 {
            onEvent(.ShowSheet(content: .date))
        } else {
            onEvent(.FilterByDate(timeMillis: 0))
        }
    }
    
    private func onSelectedCategory() {
        if uiState.selectedCategory == nil {
            onEvent(.ShowSheet(content: .category))
        } else {
            onEvent(.FilterByCategory(value: nil))
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
        uiState: TransactionsState(),
        onEvent: {_ in }
    )
}
