import SwiftUI

struct CategoriesScreen: View {
    @StateObject private var viewModel = CategoriesViewModel()
    var body: some View {
        VStack {
            CategoriesView(categories: viewModel.categories)
        }
        
        
    }
}

#Preview {
    CategoriesScreen()
}
