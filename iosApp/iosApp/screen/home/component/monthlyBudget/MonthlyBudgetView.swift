import SwiftUI
import Shared

struct MonthlyBudgetView: View {
    
    let monthlyBudgetOverView: MonthlyBudgetOverViewModel
    let onEvent: (HomeEvent) -> Void
    
    var body: some View {
        VStack(spacing: 16) {
            TopContentView(overview: monthlyBudgetOverView, onEvent: onEvent)
            HorizontalDivider()
            BottomContentView(overview: monthlyBudgetOverView)
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
}

fileprivate struct TopContentView: View {
    
    let overview: MonthlyBudgetOverViewModel
    let onEvent: (HomeEvent) -> Void
    private let imageSize: CGFloat = 24
    
    private var formattedLimit: String {
        overview.limit.toIDRCurrency()
    }
    
    private var formattedSpent: String {
        overview.spent.toIDRCurrency()
    }
    
    private var formattedLeft: String {
        overview.left.toIDRCurrency()
    }
    
    var body: some View {
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
                        onClick: { onEvent(.ToMonthlyBudget())},
                        fontWeight: .bold
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
}

fileprivate struct BottomContentView: View {
    let overview: MonthlyBudgetOverViewModel
    var body: some View {
        HStack(spacing: 16) {
            BalanceItemView(value: overview.totalIncome,isIncome: true)
            Spacer()
            BalanceItemView(value: overview.totalExpense,isIncome: false)
        }
    }
}

fileprivate struct BalanceItemView: View {
    
    let value: Double
    let isIncome: Bool
    
    private var imageName: String {
        isIncome ? "arrow.up" : "arrow.down"
    }
    
    private var text: LocalizedStringKey {
        isIncome ? "Total Income" : "Total Expenses"
    }
    
    private var formattedValue: String {
        value.toIDRCurrency()
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            HStack {
                Spacer()
                Image(systemName: imageName)
            }
            Text(text)
                .foregroundStyle(ColorTheme.outline)
                .font(Typography.bodyMedium)
            Text(formattedValue)
                .font(Typography.titleMedium)
        }
    }
}
