import SwiftUI

struct TabBarView: View {
    @Binding var currentTab: Int32
    @Namespace var nameSpace
    
    var tabBarOptions: [String] = ["View 1", "View 2"]
    
    var body: some View {
        HStack(spacing: 20) {
            ForEach(Array(zip(tabBarOptions.indices, tabBarOptions)), id: \.0){ index, name in
                TabBarItemView(
                    currentTab: $currentTab,
                    namespace: nameSpace.self,
                    tabBarItemName: name,
                    tab: Int32(index)
                )
            }
        }
        .padding(.horizontal)
        .frame(height: 40)
    }
}
