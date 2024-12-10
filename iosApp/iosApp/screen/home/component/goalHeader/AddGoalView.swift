import SwiftUI

struct AddGoalView: View {
    
    let onClick: () -> Void
    private let imageSize: CGFloat = 24
    
    var body: some View {
        HStack(spacing: 16) {
            OutlinedCircleIcon(imageName: "star.circle", size: imageSize)
            VStack(alignment: .leading, spacing: 8) {
                Text("My Goals").font(Typography.bodyMedium)
                Text("Add New Goals")
                    .font(Typography.bodyMedium)
            }
            Spacer()
            IconButtonView(name: "plus", width: imageSize, onClick: onClick)
                .padding(.trailing, 12)
        }
        .customBackground(backgroundColor: ColorTheme.surface)
        
    }
}

#Preview {
    AddGoalView(onClick: {})
}
