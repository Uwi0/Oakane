import SwiftUI
import Shared

struct CategoriesView: View {
    
    let categories: [CategoryModel]
    let onClick: (CategoryModel) -> Void
    
    var body: some View {
        List {
            ForEach(categories, id: \.id) { category in
                CategoryItemView(category: category)
                    .listRowBackground(Color.clear)
                    .listRowSeparator(.hidden)
                    .listRowInsets(EdgeInsets(top: 8, leading: 16, bottom: 8, trailing: 16))
                    .onTapGesture {
                        onClick(category)
                    }
            }
        }
        .listStyle(PlainListStyle())
        .padding(.vertical, 8)
        .scrollIndicators(.hidden)
    
    }


}
