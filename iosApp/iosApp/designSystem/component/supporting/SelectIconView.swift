import SwiftUI
import Shared
import PhotosUI

struct SelectIconView: View {
    let selectedIcon: CategoryIconName
    let selectedColor: Int32
    let onPickIcon: (CategoryIconName) -> Void
    let onTakImage: (String) -> Void
    let onConfirm: () -> Void

    var body: some View {
        VStack{
            Text("Chose Icon")
                .font(Typography.titleMedium)
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    ForEach(parentCategories, id: \.name){ parentCategory in
                        SelectionIconItemView(
                            selectedIcon: selectedIcon,
                            selectedColor: selectedColor,
                            parentCategory: parentCategory,
                            onPickIcon: onPickIcon
                        )
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                }
            }
            .scrollIndicators(.hidden)
            
            HStack(spacing: 16) {
                CategoryImagePickerView(onTakeImage: onTakImage)
                FilledButtonView(
                    text: "Select Icon",
                    onClick: onConfirm
                )
            }
            .frame(height: 48)
        }
        .padding(16)
    }
}

private struct SelectionIconItemView: View {
    let selectedIcon: CategoryIconName
    let selectedColor: Int32
    let parentCategory: ParentCategory
    let onPickIcon: (CategoryIconName) -> Void
    
    var body: some View {
        VStack(spacing: 8) {
            SelectionHeaderView(parentCategory: parentCategory)
            CategoryIconContentView(
                selectedIcon: selectedIcon,
                selectedColor: selectedColor,
                parentCategory: parentCategory,
                onPickIcon: onPickIcon
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
    let selectedIcon: CategoryIconName
    let selectedColor: Int32
    let parentCategory: ParentCategory
    let onPickIcon: (CategoryIconName) -> Void
    private let column: [GridItem] = [.init(.adaptive(minimum: 48, maximum: 48))]
    
    var body: some View {
        LazyVGrid(columns: column, alignment: .leading) {
            let categories = categoriesName(parentCategory: parentCategory)
            ForEach(categories, id: \.self) { category in
                SelectionIconView(
                    selectedColor:selectedColor,
                    selectedIcon: selectedIcon,
                    category: category,
                    onPickIcon: onPickIcon
                )
            }
        }
    }
}

struct SelectionIconView: View {
    let selectedColor: Int32
    let selectedIcon: CategoryIconName
    let category: CategoryIconName
    let onPickIcon: (CategoryIconName) -> Void
    
    private var color: Color {
        selectedIcon == category ? Color(hex: selectedColor) : ColorTheme.outline
    }
    
    private var iconName: String {
        category.asIconCategory()
    }

    var body: some View {
        CategoryIconView(icon: iconName, color: color)
            .onTapGesture {
                onPickIcon(category)
            }
    }
}

private struct CategoryImagePickerView: View {
    
    let onTakeImage: (String) -> Void
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
                        onTakeImage(imageName)
                    case .failure(let error):
                        print(error)
                    }
                }
            }
        }
    }
}
