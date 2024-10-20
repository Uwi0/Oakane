import SwiftUI
import Shared

struct FilterTypeView: View {
    @Binding var selectedType: String
    var body: some View {
        ScrollView(.horizontal) {
            HStack(alignment: .firstTextBaseline, spacing: 16) {
                ForEach(TransactionType.entries, id: \.self){ type in
                    let isSelected = selectedType == type.name
                    InputChipView(title: type.name, isSelected: isSelected, onSelected: { selectedType = type.name})
                }
            }
        }
        .scrollIndicators(.hidden)
    }
}

#Preview {
    FilterTypeView(selectedType: .constant(TransactionType.entries.first!.name))
}
