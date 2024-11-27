import SwiftUI
import Shared

struct AddTransactionScreen: View {
    
    let transactionId: Int64
    
    @StateObject private var viewModel = AddTransactionViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    var body: some View {
        ZStack {
            ColorTheme.surface.ignoresSafeArea()
            
            VStack(spacing: 16) {
                OutlinedTextFieldView(
                    value: viewModel.uiState.title,
                    placeHolder: "Title",
                    onValueChange: { newValue in viewModel.handle(event: .ChangedTitle(value: newValue)) }
                )
                OutlinedCurrencyTextFieldView(
                    label: "Amount",
                    onValueChange: { newValue in viewModel.handle(event: .ChangedAmount(value: newValue))}
                )
                SelectionPickerView(
                    title: "Transaction Type",
                    onClick: { option in
                        viewModel.handle(event: .ChangeType(value: option.asTransactionType()))
                    }
                )
                CategoryButtonView(
                    uiState: viewModel.uiState,
                    onEvent: viewModel.handle(event:)
                )
                DateButtonView(
                    title: "Today",
                    onClick: { date in
                        let convertedDate = date.toInt64()
                        viewModel.handle(event: .ChangeDate(value: convertedDate))
                    }
                )
                OutlinedTextFieldView(
                    value: viewModel.uiState.note,
                    placeHolder: "Note",
                    onValueChange: { newValue in viewModel.handle(event: .ChangeNote(value: newValue)) }
                )
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
        }
        .navigationBarBackButtonHidden(true)
        .toolbar {
            ToolbarItem(placement: .topBarLeading) {
                IconButtonView(
                    name: "arrow.left",
                    size: 16,
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
}
