import SwiftUI

struct CategoriesScreen: View {
    @StateObject private var viewModel = CategoriesViewModel()
    var body: some View {
        ScrollView {
            ForEach(viewModel.categories, id: \.id) { category in
                HStack(spacing: 16) {
                    Image(systemName: category.name.asIconCategory())
                    Text(category.name)
                    Spacer()
                }
            }
        }
        .scrollIndicators(.hidden)
        
    }
}

#Preview {
    CategoriesScreen()
}
