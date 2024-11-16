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
                OutlinedTextFieldView(value: $viewModel.uiState.title, placeHolder: "Title")
                    .onChange(of: viewModel.uiState.title) { title in
                        viewModel.handle(event: .ChangedTitle(value: title))
                    }
                OutlinedNumericTextFieldView(value: $viewModel.uiState.amount, placeHolder: "Amount")
                    .onChange(of: viewModel.uiState.amount) { amount in
                        viewModel.handle(event: .ChangedAmount(value: amount))
                    }
                SelectionPickerView(
                    title: "Transaction Type",
                    options: viewModel.transactionOptions,
                    onClick: { option in
                        viewModel.handle(event: .ChangeType(value: option.asTransactionType()))
                    }
                )
//                SelectionPickerView(
//                    title: "Category",
//                    options: viewModel.categoryOptions,
//                    selectedOpion: $viewModel.selectedCategoryOption
//                )
                DateButtonView(
                    title: "Today",
                    onClick: { date in
                        let convertedDate = Int64(date.timeIntervalSince1970)
                        viewModel.handle(event: .ChangeDate(value: convertedDate))
                    }
                )
                OutlinedTextFieldView(
                    value: $viewModel.uiState.note,
                    placeHolder: "Note"
                ).onChange(of: viewModel.uiState.note) { note in
                    viewModel.handle(event: .ChangeNote(value: note))
                }
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
