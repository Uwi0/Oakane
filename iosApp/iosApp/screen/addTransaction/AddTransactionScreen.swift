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
        ZStack {
            ColorTheme.surface.ignoresSafeArea()
            AddTransactionContentView()
        }
        .navigationBarBackButtonHidden(true)
        .toolbar {
            ToolbarItem(placement: .topBarLeading) {
                IconButtonView(
                    name: "arrow.left",
                    width: 16,
                    onClick: {
                        navigation.navigateBack()
                    }
                )
            }
            ToolbarItem(placement: .topBarLeading) {
                Text("Add Transaction")
                    .font(Typography.titleMedium)
            }
        }
        .onAppear {
            viewModel.initializeData(transactionId: transactionId)
        }
    }
    
    @ViewBuilder
    private func AddTransactionContentView() -> some View {
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
                value: $viewModel.uiState.amount,
                onValueChange: { newValue in viewModel.handle(event: .ChangedAmount(value: newValue))}
            )
            SelectionPickerView(
                title: "Transaction Type",
                label: "Type",
                selectedOption: Binding(
                    get: { uiState.selectedType},
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
            Spacer()
            FilledButtonView(
                text: "Save Transaction",
                onClick: {
                    viewModel.handle(event: .SaveTransaction())
                    navigation.navigateBack()
                }
            )
            .frame(height: 48)
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 24)
        .sheet(
            isPresented: $viewModel.uiState.showSheet,
            onDismiss: { viewModel.handle(event: .Sheet(shown: false)) },
            content: {
                CategoriesSheetView(
                    categories: viewModel.uiState.categories,
                    onEvent: viewModel.handle(event:)
                )
                .presentationDetents([.height(640)])
                .presentationDragIndicator(.visible)
            }
        )
        .sheet(isPresented: $showCamera, content: { CameraViewController() })
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
            ButtonPickImage()
        }
    }
}


struct CameraViewController: UIViewControllerRepresentable {
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
                        print(url)
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
