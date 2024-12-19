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
            CreateCategoryButtonView(uiState: uiState, onEvent: onEvent)
        }
        .padding(16)
    }
}

private struct TitleView: View {
    let title: String
    var body: some View {
        Text(title).font(Typography.titleMedium)
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
            SelectedIconView(
                imageName: uiState.imageName,
                icon: uiState.selectedIcon,
                color: uiState.selectedColor
            )
            .onTapGesture {
                onEvent(.ChangeSheet(content: .selectIcon))
            }
            
            OutlinedTextFieldView(
                value: $categoryName,
                placeHolder: "Category Name",
                showLabel: false,
                onValueChange: { newValue in onEvent(.ChangeCategory(name: newValue)) }
            )
        }
        
    }
}


private struct CategorySegmentedButtonView: View {
    
    let onEvent: (CategoriesEvent) -> Void
    
    @State private var selectedType: TransactionType = .income
    
    var body: some View {
        Picker("Transaction Type", selection: $selectedType) {
            ForEach(TransactionType.allCases, id: \.self) { type in
                Text(type.name)
            }
        }
        .pickerStyle(.segmented)
        .onChange(of: selectedType) {
            onEvent(.Selected(type: selectedType))
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
                        .onChange(of: selectedColor) {
                            let colorHex = selectedColor.toHexString() ?? "0xFFFFFF"
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

private struct CreateCategoryButtonView: View {
    let uiState: CategoriesState
    let onEvent: (CategoriesEvent) -> Void
    
    private var isEditMode: Bool {
        uiState.isEditMode
    }
    private var saveTitle: String {
        isEditMode ? "Update" : "Create"
    }
    var body: some View {
        HStack {
            if isEditMode {
                OutlinedButtonView(
                    text: "Delete",
                    onClick: {onEvent(.SwipeToDeleteBy(id: uiState.categoryId))},
                    color: ColorTheme.error
                )
            }
            
            FilledButtonView(text: saveTitle, onClick: { onEvent(.SaveCategory())})
            
        }
        .frame(height: 48)
    }
}

#Preview {
    CreateCategoryContentView(uiState: CategoriesState(), onEvent: {_ in })
}
