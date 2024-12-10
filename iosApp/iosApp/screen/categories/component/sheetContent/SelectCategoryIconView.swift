import SwiftUI
import Shared
import PhotosUI

struct SelectCategoryIconView: View {
    let uiState: CategoriesState
    let onEvent: (CategoriesEvent) -> Void
    let onClickedFromGallery: () -> Void
    
    var body: some View {
        VStack{
            Text("Chose Icon")
                .font(Typography.titleMedium)
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    ForEach(parentCategories, id: \.name){ parentCategory in
                        SelectionIconItemView(
                            uiState: uiState,
                            parentCategory: parentCategory,
                            onEvent: { iconName in
                                onEvent(.SelectedIcon(name: iconName))
                            }
                        )
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                }
            }
            .accessibilityHidden(true)
            
            HStack(spacing: 16) {
                CategoryImagePickerView(onEvent: onEvent)
                FilledButtonView(
                    text: "Select Icon",
                    onClick: {
                        onEvent(.ConfirmIcon())
                    }
                )
            }
            .frame(height: 48)
        }
        .padding(16)
    }
}

private struct SelectionIconItemView: View {
    let uiState: CategoriesState
    let parentCategory: ParentCategory
    let onEvent: (CategoryIconName) -> Void
    
    var body: some View {
        VStack(spacing: 8) {
            SelectionHeaderView(parentCategory: parentCategory)
            CategoryIconContentView(
                uiState: uiState,
                parentCategory: parentCategory,
                onEvent: onEvent
            )
        }
    }
}

private struct SelectionHeaderView: View {
    
    let parentCategory: ParentCategory
    
    var body: some View {
        HStack(alignment: .center) {
            Text(parentCategory.displayName)
                .font(Typography.titleSmall)
                .padding(.horizontal, 8)
            Spacer()
        }
        .frame(maxWidth: .infinity)
    }
}

private struct CategoryIconContentView: View {
    let uiState: CategoriesState
    let parentCategory: ParentCategory
    let onEvent: (CategoryIconName) -> Void
    private let column: [GridItem] = [.init(.adaptive(minimum: 48, maximum: 48))]
    
    var body: some View {
        LazyVGrid(columns: column, alignment: .leading) {
            let categories = categoriesName(parentCategory: parentCategory)
            ForEach(categories, id: \.self) { category in
                SelectionIconView(uiState: uiState,category: category, onEvent: onEvent)
            }
        }
    }
}

struct SelectionIconView: View {
    let uiState: CategoriesState
    let category: CategoryIconName
    let onEvent: (CategoryIconName) -> Void
    
    private var color: Color {
        uiState.selectedIcon == category ? Color(hex: uiState.selectedColor) : ColorTheme.outline
    }
    
    private var iconName: String {
        category.asIconCategory()
    }

    var body: some View {
        CategoryIconView(icon: iconName, color: color)
            .onTapGesture {
                onEvent(category)
            }
    }
}

private struct CategoryImagePickerView: View {
    
    let onEvent: (CategoriesEvent) -> Void
    @State private var selectedItem: PhotosPickerItem? = nil
    @State private var selectedImage: UIImage? = nil
    
    var body: some View {
        PhotosPicker(
            selection: $selectedItem,
            matching: .images,
            photoLibrary: .shared()
        ){
            Text("From Gallery")
                .font(.headline)
                .foregroundStyle(ColorTheme.primary)
                .padding(.horizontal, 24)
                .padding(.vertical, 16)
                .frame(maxWidth: .infinity)
                .overlay {
                    RoundedRectangle(cornerRadius: 16)
                        .stroke(ColorTheme.primary, lineWidth: 3)
                }
        }
        .onChange(of: selectedItem){
            Task {
                if let data = try? await selectedItem?.loadTransferable(type: Data.self),
                   let uiImage = UIImage(data: data) {
                    selectedImage = uiImage
                    let savedImage = saveImageToFileSystem(image: uiImage)
                    switch savedImage {
                    case .success(let imageName):
                        onEvent(.PickImage(file: imageName))
                    case .failure(let error):
                        print(error)
                    }
                }
            }
        }
    }
}

#Preview {
    SelectCategoryIconView(
        uiState: CategoriesState(),
        onEvent: {_ in },
        onClickedFromGallery: { }
    )
}
