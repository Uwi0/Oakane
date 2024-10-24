import SwiftUI

struct AddTransactionScreen: View {
    
    let transactionId: Int64
    
    @StateObject private var viewModel = AddTRansactionViewModel()
    @EnvironmentObject private var navigation: AppNavigation
    
    var body: some View {
        ZStack {
            ColorTheme.surface.ignoresSafeArea()
            
            VStack(spacing: 16) {
                OutlinedTextFieldView(value: $viewModel.title, placeHolder: "Title")
                OutlinedNumericTextFieldView(value: $viewModel.amount, placeHolder: "Amount")
                SelectionPickerView(
                    title: "Transaction Type",
                    options: viewModel.transactionOptions,
                    selectedOpion: $viewModel.selectedTransactionOption
                )
                SelectionPickerView(
                    title: "Category",
                    options: viewModel.categoryOptions,
                    selectedOpion: $viewModel.selectedCategoryOption
                )
                DateButtonView(date: $viewModel.selectedDate.animation(),title: "Today", onClick: { viewModel.showDatePicker.toggle()})
                OutlinedTextFieldView(value: $viewModel.note, placeHolder: "Note")
                Spacer()
                FilledButtonView(
                    text: "Save Transaction",
                    onClick: {
                        viewModel.save()
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
