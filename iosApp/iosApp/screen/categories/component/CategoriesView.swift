import SwiftUI
import Shared

struct CategoriesView: View {
    
    let categories: [CategoryModel]
    
    var body: some View {
        ScrollView {
            ForEach(categories, id: \.id) { category in
                CategoryItemView(category: category)
            }
            .padding(.vertical, 24)
            .padding(.horizontal, 16)
        }
        .scrollIndicators(.hidden)
    }
}
