import SwiftUI
import Shared

struct FilterByTypeView: View {
    @Binding var selectedType: String
    let onClick: () -> Void
    var body: some View {
        VStack(alignment: .leading,spacing: 16) {
            
            Text("Filter by Type")
                .font(Typography.titleSmall)
            
            ScrollView(.horizontal) {
                HStack(alignment: .firstTextBaseline, spacing: 16) {
                    ForEach(TransactionType.allCases, id: \.self){ type in
                        let isSelected = selectedType == type.name
                        InputChipView(title: type.name, isSelected: isSelected, onSelected: { selectedType = type.name})
                    }
                }
            }
            .scrollIndicators(.hidden)
            
            FilledButtonView(text: "Apply filter", onClick: onClick)
                .frame(height: 48)
        }
        .padding(.vertical, 24)
        .padding(.horizontal, 16)
        
    }
}

#Preview {
    FilterByTypeView(selectedType: .constant(TransactionType.allCases.first!.name), onClick: {})
}
