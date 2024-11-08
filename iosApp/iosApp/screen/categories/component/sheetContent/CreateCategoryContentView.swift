import SwiftUI
import Shared

struct CreateCategoryContentView: View {
    
    let uiState: CategoriesState
    let onEvent: (CategoriesEvent) -> Void
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Spacer()
            TitleView(title: "Create Category")
            CategoryNameFieldView(uiState: uiState, onEvent: onEvent)
            TitleView(title: "Category Type")
            CategorySegmentedButtonView(onEvent: onEvent)
            TitleView(title: "Category Color")
            CategoryColorSeleectionView(uiState: uiState, onEvent: onEvent)
            Spacer()
            FilledButtonView(text: "Save Category", onClick: { onEvent(.SaveCategory())})
                .frame(height: 48)
        }
        .padding(16)
    }
}

private struct TitleView: View {
    let title: String
    var body: some View {
        Text(title).font(Typography.titleSmall)
    }
}

private struct CategoryNameFieldView: View {
    @State private var categoryName: String = ""
    let uiState: CategoriesState
    let onEvent: (CategoriesEvent) -> Void
    
    init(uiState: CategoriesState, onEvent: @escaping (CategoriesEvent) -> Void) {
        categoryName = uiState.categoryName
        self.uiState = uiState
        self.onEvent = onEvent
    }
    
    
    var body: some View {
        HStack(spacing: 16) {
            SelectedCategoryIconView(uiState: uiState)
            .onTapGesture {
                onEvent(.ChangeSheet(content: .selectIcon))
            }
            
            OutlinedTextFieldView(value: $categoryName, placeHolder: "Category Name")
        }
        .onChange(of: categoryName){ newCategoryName in
            onEvent(.ChangeCategory(name: newCategoryName))
        }
        
    }
}

private struct SelectedCategoryIconView: View {
    
    let uiState: CategoriesState
    
    private var icon: String {
        uiState.selectedIcon.asIconCategory()
    }
    
    private var color: Color {
        Color(hex: uiState.selectedColor)
    }
    
    var body: some View {
        VStack{
            if uiState.imageName.isEmpty {
                CategoryIconView(icon: icon, color: color)
            } else {
                DisplayImageFileView(fileName: uiState.imageName, width: 48, height: 48)
                    .overlay{
                        Circle()
                            .stroke(color, lineWidth: 3)
                    }
            }
        }
    }
}


private struct CategorySegmentedButtonView: View {
    
    let onEvent: (CategoriesEvent) -> Void
    
    @State private var selectedType: TransactionType = .expense
    
    var body: some View {
        Picker("Transaction Type", selection: $selectedType) {
            ForEach(TransactionType.allCases, id: \.self) { type in
                Text(type.name)
            }
        }
        .pickerStyle(.segmented)
        .onChange(of: selectedType) { newSelectedType in
            onEvent(.Selected(type: newSelectedType))
        }
    }
}

private struct CategoryColorSeleectionView: View {
    
    let uiState: CategoriesState
    let onEvent: (CategoriesEvent) -> Void
    @State private var selectedColor: Color
    
    init(uiState: CategoriesState, onEvent: @escaping (CategoriesEvent) -> Void) {
        self.uiState = uiState
        self.onEvent = onEvent
        self.selectedColor = Color(hex: uiState.selectedColor)
    }
    
    var body: some View {
        ScrollView(.horizontal) {
            HStack(spacing: 16) {
                CategoryIconView(
                    icon: IconConstant.Paintbrush,
                    color: Color(hex:uiState.selectedColor)
                )
                .allowsHitTesting(false)
                .background {
                    ColorPicker("CustomColor",selection: $selectedColor)
                        .labelsHidden()
                        .onChange(of: selectedColor) { newSelectedColor in
                            let colorHex = newSelectedColor.toHexString() ?? "0xFFFFFF"
                            onEvent(.SelectedColor(hex: colorHex))
                        }
                }
                ForEach(uiState.defaultColors, id: \.self) { hex in
                    let colorHex = hex.toColorInt()
                    let color = Color(hex: colorHex)
                    Circle()
                        .fill(color)
                        .frame(width: 48, height: 48)
                        .onTapGesture {
                            onEvent(.SelectedColor(hex: hex))
                        }
                }
            }
        }
        .scrollIndicators(.hidden)
    }
}

#Preview {
    CreateCategoryContentView(uiState: CategoriesState(), onEvent: {_ in })
}
