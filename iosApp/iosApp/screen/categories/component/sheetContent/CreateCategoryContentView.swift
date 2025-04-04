import SwiftUI
import Shared

struct CreateCategoryContentView: View {
    
    let uiState: CategoriesState
    let onEvent: (CategoriesEvent) -> Void
    
    @State private var selectedType: TransactionType = .income
    
    private var formattedColor: Color {
        Color(hex: uiState.selectedColor.toColorLong())
    }
    
    private var saveTitle: String {
        uiState.isEditMode ? "Update" : "Create"
    }
    
    private var colors: [Color] {
        uiState.categoriesColor.map{ color in Color(hex: color.toColorLong())}
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Spacer()
            TitleView("Create Category")
            CategoryNameFieldView()
            TitleView("Category Type")
            CategorySegmentedButtonView()
            TitleView("Category Color")
            HorizontalColorSelectorView(
                colors: colors,
                selectedColor: Binding(
                    get: { Color(hex: uiState.selectedColor.toColorLong())},
                    set: { color in onEvent(.SelectedColor(hex: color.toHexString())) }
                )
            )
            Spacer().frame(height: 48)
            CreateCategoryButtonView()
        }
        .padding(16)
    }
    
    @ViewBuilder
    private func TitleView(_ title: String) -> some View {
        Text(title).font(Typography.titleMedium)
    }
    
    @ViewBuilder
    private func CategoryNameFieldView() -> some View {
        HStack(spacing: 16) {
            SelectedIconView(
                imageName: uiState.fileName,
                icon: uiState.selectedIcon,
                color: formattedColor
            )
            .onTapGesture {
                onEvent(.ChangeSheet(content: .selectIcon))
            }
            
            OutlinedTextFieldView(
                value: Binding(
                    get: { uiState.categoryName },
                    set: {newValue in onEvent(.ChangeCategory(name: newValue)) }
                ),
                placeHolder: "Category Name",
                showLabel: false
            )
        }
    }
    
    @ViewBuilder
    private func CategorySegmentedButtonView() -> some View {
        Picker("Transaction Type", selection: $selectedType) {
            ForEach(TransactionType.allCases, id: \.self) { type in Text(type.name) }
        }
        .pickerStyle(.segmented)
        .onChange(of: selectedType) {
            onEvent(.Selected(type: selectedType))
        }
    }
    
    @ViewBuilder
    private func CreateCategoryButtonView() -> some View {
        HStack {
            if uiState.isEditMode {
                OutlinedButtonView(
                    text: "Delete",
                    onClick: { onEvent(.SwipeToDeleteBy(id: uiState.categoryId)) },
                    color: ColorTheme.error
                )
            }
            
            FilledButtonView(text: saveTitle, onClick: { onEvent(.SaveCategory())})
            
        }
        .frame(height: 48)
    }
}

#Preview {
    CreateCategoryContentView(uiState: CategoriesState.companion.default(), onEvent: {_ in })
}
