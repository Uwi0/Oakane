import SwiftUI

struct CardContentWithIconView: View {
    
    let icon: String
    let title: String
    let content: String
    
    var body: some View {
        HStack(spacing: 8) {
            
            Image(systemName: icon)
                .foregroundStyle(ColorTheme.outline)
                .fontWeight(.bold)
                .frame(width: 24, height: 24)
            
            VStack(spacing: 4) {
                
                Text(title)
                    .font(Typography.titleSmall)
                    .foregroundStyle(ColorTheme.outline)
                
                Text(content)
                    .font(Typography.bodySmall)
                    .foregroundStyle(ColorTheme.primary)
                
            }
        }
    }
    
}
