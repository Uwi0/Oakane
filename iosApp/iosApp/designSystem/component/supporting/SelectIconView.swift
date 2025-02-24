import SwiftUI
import Shared
import PhotosUI

struct SelectIconView: View {
    @Binding var selectedIcon: CategoryIconName
    let selectedColor: Color
    let onTakeImage: (String) -> Void
    let onConfirm: () -> Void

    var body: some View {
        VStack{
            Text("Chose Icon").font(Typography.titleMedium)
            ScrollView {
                IconListView()
            }
            .scrollIndicators(.hidden)
            
            HStack(spacing: 16) {
                CategoryImagePickerView(onTakeImage: onTakeImage)
                FilledButtonView(
                    text: "Select Icon",
                    onClick: onConfirm
                )
            }
            .frame(height: 48)
        }
        .padding(16)
    }
    
    @ViewBuilder
    private func IconListView() -> some View {
        VStack(alignment: .leading, spacing: 16) {
            ForEach(parentCategories, id: \.name){ parentCategory in
                SelectionIconItemView(
                    selectedIcon: $selectedIcon,
                    selectedColor: selectedColor,
                    parentCategory: parentCategory
                )
            }
            .frame(maxWidth: .infinity, alignment: .leading)
        }
    }
}

private struct SelectionIconItemView: View {
    @Binding var selectedIcon: CategoryIconName
    let selectedColor: Color
    let parentCategory: ParentCategory
    
    var body: some View {
        VStack(spacing: 8) {
            SelectionHeaderView(parentCategory: parentCategory)
            CategoryIconContentView(
                selectedIcon: $selectedIcon,
                selectedColor: selectedColor,
                parentCategory: parentCategory
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
    @Binding var selectedIcon: CategoryIconName
    let selectedColor: Color
    let parentCategory: ParentCategory
    private let column: [GridItem] = [.init(.adaptive(minimum: 48, maximum: 48))]
    
    var body: some View {
        LazyVGrid(columns: column, alignment: .leading) {
            let categories = categoriesName(parentCategory: parentCategory)
            ForEach(categories, id: \.self) { category in
                SelectionIconView(
                    selectedColor:selectedColor,
                    selectedIcon: $selectedIcon,
                    category: category
                )
            }
        }
    }
}

struct SelectionIconView: View {
    let selectedColor: Color
    @Binding var selectedIcon: CategoryIconName
    let category: CategoryIconName
    
    private var color: Color {
        selectedIcon == category ? selectedColor : ColorTheme.outline
    }
    
    private var iconName: String {
        category.asIconCategory()
    }

    var body: some View {
        CategoryIconView(icon: iconName, color: color)
            .onTapGesture {
                selectedIcon = category
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
                .font(Typography.labelLarge)
                .foregroundStyle(ColorTheme.primary)
                .padding()
                .frame(maxWidth: .infinity)
                .overlay {
                    RoundedRectangle(cornerRadius: 16)
                        .stroke(ColorTheme.primary, lineWidth: 3)
                }
        }
        .onChange(of: selectedItem){
            getImageLocalIdentifier()
        }
    }
    
    func getImageLocalIdentifier() {
        guard let selectedItem else { return }
        
        Task {
            do {
                guard let imageData = try await selectedItem.loadTransferable(type: Data.self),
                      let image = UIImage(data: imageData) else { return }
                
                selectedImage = image
                handleImageSaving(image)
            } catch {
                print("Error loading image: \(error.localizedDescription)")
            }
        }
    }

    private func handleImageSaving(_ image: UIImage) {
        saveImageToPhotoLibrary(image: image) { result in
            switch result {
            case .success(let imageName):
                onTakeImage(imageName)
            case .failure(let error):
                print("Error saving image: \(error.localizedDescription)")
            }
        }
    }
}
