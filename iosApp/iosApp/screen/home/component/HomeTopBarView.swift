import SwiftUI

struct HomeTopBarView: View {
    
    let onClick: () -> Void
    
    var body: some View {
        HStack {
            ZStack {
                Button(
                    action: onClick,
                    label: {
                        Image(systemName: "line.3.horizontal")
                            .tint(ColorTheme.onSurface)
                            .font(.title)
                    }
                )
            }
            .frame(width: 48, height: 48, alignment: .center)
            Spacer()
            Text("Dashboard")
                .font(Typography.titleMedium)
            
            Spacer()
            ZStack {
                
            }
            .frame(width: 48, height: 48)
        }
        .frame(maxWidth: .infinity, alignment: .center)
    }
}

#Preview {
    HomeTopBarView(onClick: {})
}
