import SwiftUI
import Shared

struct MonthlyBudgetView: View {
    
    let overview: MonthlyBudgetOverView
    let onEvent: (HomeEvent) -> Void
    
    private let imageSize: CGFloat = 24
    
    private var formattedLimit: String {
        overview.limit.toFormatCurrency(currency: overview.currency)
    }
    
    private var formattedSpent: String {
        overview.spent.toFormatCurrency(currency: overview.currency)
    }
    
    private var formattedLeft: String {
        overview.left.toFormatCurrency(currency: overview.currency)
    }
    
    var body: some View {
        VStack(spacing: 16) {
            TopContentView()
            HorizontalDivider()
            BottomContentView()
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
    
    @ViewBuilder
    private func TopContentView() -> some View {
        HStack(alignment: .center, spacing: 16) {
            OutlinedCircleIcon(imageName: "dollarsign", size: imageSize)
            VStack(alignment: .leading,spacing: 8) {
                HStack(alignment: .center) {
                    Text("Montly budget")
                        .font(Typography.titleMedium)
                    Spacer()
                    IconButtonView(
                        name: "pencil",
                        width: imageSize,
                        onClick: { onEvent(.ToMonthlyBudget())}
                    )
                }
                Text(formattedLimit)
                    .font(.title3)
                    .fontWeight(.semibold)
                ProgressIndicatorView(value: overview.progress)
                HStack{
                    Text("Spent \(formattedSpent)")
                        .font(Typography.bodyMedium)
                    Spacer()
                    Text("Left \(formattedLeft)")
                        .font(Typography.bodyMedium)
                }
            }
        }
    }
    
    @ViewBuilder
    private func BottomContentView() -> some View {
        HStack(spacing: 16) {
            BalanceItemView(value: overview.totalIncome, currency: overview.currency, isIncome: true)
            Spacer()
            BalanceItemView(value: overview.totalExpense, currency: overview.currency, isIncome: false)
        }
    }
    
    @ViewBuilder
    private func BalanceItemView(value: Double, currency: Currency, isIncome: Bool) -> some View {
        let text = isIncome ? "Total Income" : "Total Expenses"
        let formattedValue = value.toFormatCurrency(currency: currency)
        let transactionType: TransactionType = isIncome ? .income : .expense
        
        VStack(alignment: .leading, spacing: 8) {
            HStack {
                Spacer()
                TransactionTypeIconView(transactionType: transactionType, iconSize: 24)
            }
            Text(text)
                .foregroundStyle(ColorTheme.outline)
                .font(Typography.bodyMedium)
            Text(formattedValue)
                .font(Typography.titleMedium)
        }
    }
    
}

#Preview {
    MonthlyBudgetView(overview: defaultMonthlyBudgetOverView, onEvent: { _ in })
}
