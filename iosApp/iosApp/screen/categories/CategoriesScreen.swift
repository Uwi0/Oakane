import SwiftUI

struct CategoriesScreen: View {
    @StateObject private var viewModel = CategoriesViewModel()
    var body: some View {
        ForEach(viewModel.categories, id: \.id) { category in
            Text(category.name)
        }
    }
}

#Preview {
    CategoriesScreen()
}
