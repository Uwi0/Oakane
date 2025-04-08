import SwiftUI
import Shared

struct FilterLogSheet: View {
    
    @State private var selectedDateFilter: FilterWalletLogByDateModel = .All()
    @State private var selectedCategoryFilter: FilterWalletLogByCategory = .all
    @State private var startDate: Date = Date()
    @State private var endDate: Date = Date()
    let onEVent: (WalletEvent) -> Void
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            TitleSheet()
            Spacer().frame(height: 8)
            FilterByDateRangeComponent()
            Divider()
            FilterByCategoryComponent()
            Spacer()
            ApplyFilterButton()
        }
        .padding(.vertical, 24)
        .padding(.horizontal, 16)
        .background(ColorTheme.surface)
    }
    
    @ViewBuilder
    private func TitleSheet() -> some View {
        HStack {
            Text("Filter Log")
                .font(Typography.headlineSmall)
                .foregroundStyle(ColorTheme.onSurfaceVariant)
            Spacer()
        }
    }
    
    @ViewBuilder
    private func FilterByDateRangeComponent() -> some View {
        VStack(alignment: .leading, spacing: 4) {
            Text("Time Range").font(Typography.titleMedium)
            FilterByDateRangePicker()
            if selectedDateFilter is FilterWalletLogByDateModel.Custom {
                FilterByCustomDate()
            }
        }
    }
    
    @ViewBuilder
    private func FilterByDateRangePicker() -> some View {
        let filtersDate = FilterWalletLogByDateModel.companion.filters()
        HStack {
            Text("Date Range Filter : ")
                .font(Typography.labelLarge)
            Spacer()
            Picker("Date Range Picker", selection: $selectedDateFilter) {
                ForEach(filtersDate, id:\.id) { filter in
                    Text(filter.asTitle()).tag(filter)
                }
            }
            .pickerStyle(.menu)
            .tint(ColorTheme.primary)
        }
    }
    
    @ViewBuilder
    private func FilterByCustomDate() -> some View {
        HStack(spacing: 16) {
            FilterButtonDateView(title: "From", date: $startDate)
            FilterButtonDateView(title: "To", date: $endDate)
        }
    }
    
    @ViewBuilder
    private func FilterByCategoryComponent() -> some View {
        VStack(alignment: .leading, spacing: 4) {
            Text("Category").font(Typography.titleMedium)
            FilterByCategoryPicker()
        }
    }
    
    @ViewBuilder
    private func FilterByCategoryPicker() -> some View {
        let filtersCategory = FilterWalletLogByCategory.allCases
        HStack {
            Text("Category Transaction Filter: ")
                .font(Typography.labelLarge)
            Spacer()
            Picker("Category Picker", selection: $selectedCategoryFilter) {
                ForEach(filtersCategory, id: \.ordinal){ filter in
                    Text(filter.name).tag(filter)
                }
            }
            .pickerStyle(.menu)
            .tint(ColorTheme.primary)
        }
    }
    
    @ViewBuilder
    private func ApplyFilterButton() -> some View {
        FilledContentButtonView(onClick: {}) {
            Text("Apply Filter")
        }
    }
}

#Preview {
    FilterLogSheet(onEVent: { _ in })
}
