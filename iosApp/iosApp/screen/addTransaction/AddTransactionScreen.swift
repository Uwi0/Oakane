import SwiftUI
import Shared

struct AddTransactionScreen: View {
    
    let transactionId: Int64
    
    @StateObject private var viewModel = AddTransactionViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    @State private var showCamera: Bool = false
    
    private var uiState: AddTransactionState {
        viewModel.uiState
    }
    
    var body: some View {
        VStack {
            NavigationBar()
            AddTransactionContentView()
            FilledButtonView(
                text: "Save Transaction",
                onClick: {
                    viewModel.handle(event: .SaveTransaction())
                    navigation.navigateBack()
                }
            )
            .frame(height: 48)
            .padding(.horizontal, 16)
        }
        .navigationBarBackButtonHidden(true)
        .background(ColorTheme.surface)
        .onAppear {
            viewModel.initializeData(transactionId: transactionId)
        }
    }
    
    @ViewBuilder
    private func NavigationBar() -> some View {
        VStack {
            NavigationTopAppbar(
                title: "Add transaction",
                onAction: { navigation.navigateBack() }
            )
            Divider()
        }
    }
    
    @ViewBuilder
    private func AddTransactionContentView() -> some View {
        ScrollView {
            VStack(spacing: 16) {
                OutlinedTextFieldView(
                    value: Binding(
                        get: { uiState.title },
                        set: { newValue in viewModel.handle(event: .ChangedTitle(value: newValue))}
                    ),
                    placeHolder: "Title"
                )
                OutlinedCurrencyTextFieldView(
                    label: "Amount",
                    value: Binding(
                        get: { Int(uiState.amount) },
                        set: { amount in viewModel.handle(event: .ChangedAmount(value: String(amount)))}
                    ),
                    onValueChange: { newValue in viewModel.handle(event: .ChangedAmount(value: newValue))}
                )
                SelectionPickerView(
                    title: "Transaction Type",
                    label: "Type",
                    selectedOption: Binding(
                        get: { uiState.transactionType.name },
                        set: { option in viewModel.handle(event: .ChangeType(value: option.asTransactionType()))}
                    )
                )
                CategoryButtonView(
                    uiState: viewModel.uiState,
                    onEvent: viewModel.handle(event:)
                )
                DateButtonView(
                    title: "Today",
                    label: "Date",
                    onClick: { date in
                        let convertedDate = date.toInt64()
                        viewModel.handle(event: .ChangeDate(value: convertedDate))
                    }
                )
                OutlinedTextFieldView(
                    value: Binding(
                        get: { uiState.note },
                        set: { newValue in viewModel.handle(event: .ChangeNote(value: newValue)) }),
                    placeHolder: "Note"
                )
                ButtonAddImage()
                
                if !uiState.imageFileName.isEmpty {
                    TransactionImagePreView(
                        imageUrl: uiState.imageFileName,
                        onDismiss: { viewModel.handle(event: .ClearImage())}
                    )
                }
                if uiState.transactionType == .expense {
                    ToggleExcludeFromBudget()
                }
                Spacer()
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 24)
        }
        .scrollIndicators(.hidden)
        .sheet(
            isPresented: Binding(
                get: { uiState.sheetShown},
                set: { isSheetShown in viewModel.handle(event: .Sheet(shown: isSheetShown))}
            ),
            content: {
                CategoriesSheetView(
                    categories: viewModel.uiState.categories,
                    onEvent: viewModel.handle(event:)
                )
                .presentationDetents([.height(640)])
                .presentationDragIndicator(.visible)
                .background(ColorTheme.surface)
            }
        )
        .sheet(
            isPresented: $showCamera,
            content: {
                CameraViewController(
                    onGetPhoto: { imageUrl in
                        viewModel.handle(event: .SaveImageFile(name: imageUrl))
                    }
                )
            }
        )
    }
    
    
    @ViewBuilder
    private func ButtonAddImage() -> some View {
        HStack(spacing: 16) {
            OutlinedContentButtonView(
                onClick: { showCamera = true },
                content: {
                    Text("Photo")
                    Spacer()
                    Image(systemName: "camera")
                }
            )
            ButtonPickImage(
                onSelectedImage: { imageName in
                    viewModel.handle(event: .SaveImageFile(name: imageName))
                }
            )
        }
    }
    
    @ViewBuilder
    private func ToggleExcludeFromBudget() -> some View {
        Toggle(
            isOn: Binding(
                get: { viewModel.uiState.excludeFromBudget },
                set: { toggled in viewModel.handle(event: .ExcludeFromBudget(value: toggled))}
            )
        ) {
            Text("Exclude from budget")
        }
    }
}

struct CameraViewController: UIViewControllerRepresentable {
    
    let onGetPhoto: (String) -> Void
    
    func makeUIViewController(context: Context) -> UIImagePickerController {
        let imagePicker = UIImagePickerController()
        imagePicker.sourceType = .camera
        imagePicker.delegate = context.coordinator
        return imagePicker
    }
    
    func updateUIViewController(_ uiViewController: UIImagePickerController, context: Context) {}
    
    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }
    
    class Coordinator: NSObject, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
        var parent: CameraViewController
        
        init(_ parent: CameraViewController) {
            self.parent = parent
        }
        
        func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
            if let capturedImage = info[.originalImage] as? UIImage {
                saveImageToPhotoLibrary(image: capturedImage) { result in
                    switch result {
                    case .success(let url):
                        self.parent.onGetPhoto(url)
                    case .failure(let error):
                        print(error)
                    }
                }
            }
            picker.dismiss(animated: true)
        }
    }
}

#Preview {
    AddTransactionScreen(transactionId: 0)
}
