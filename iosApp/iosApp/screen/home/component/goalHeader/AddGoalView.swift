import SwiftUI

struct AddGoalView: View {
    
    private let imageSize: CGFloat = 24
    
    var body: some View {
        HStack(spacing: 16) {
            OutlinedCircleIcon(imageName: "star.circle", size: imageSize)
            VStack(spacing: 8) {
                Text("My Goals").font(Typography.titleMedium)
                Text("Add New Goals")
            }
            Spacer()
            IconButtonView(name: "plus", size: imageSize, onClick: {})
                .padding(.trailing, 12)
        }
        .customBackground(backgroundColor: ColorTheme.surface)
        
    }
}

#Preview {
    AddGoalView()
}
