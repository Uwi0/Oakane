import SwiftUI
import Shared

struct SelectCurrencyContentView: View {
    
    @State var query: String = ""
    @State var selectedCurrency: Currency = .idr
    private var currencies: [Currency] {
        Currency.allCases.filter { currency in
            query.isEmpty || currency.countryName.lowercased().contains(query.lowercased())
        }
    }
    
    var body: some View {
        VStack {
            TopAppBar()
            Spacer().frame(height: 8)
            Divider()
            ScrollView {
                CurrenciesView()
            }
            .scrollIndicators(.hidden)
            FilledButtonView(text: "Confirm Currency", onClick: {})
                .frame(height: 48)
                .padding(.horizontal, 16)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
        .background(ColorTheme.surface.ignoresSafeArea())
    }
    
    @ViewBuilder private func TopAppBar() -> some View {
        VStack(alignment: .leading) {
            Text("Set Currency").font(Typography.displayMedium)
            Spacer().frame(height: 24)
            OutlinedSearchTextFieldView(query: $query, placeHolder: "Search Currency")
            Spacer().frame(height: 8)
            Text("Selected Currency: \(selectedCurrency.name)").font(Typography.bodyMedium).foregroundStyle(ColorTheme.primary)
        }
        .padding(.horizontal, 16)
    }
    
    @ViewBuilder private func CurrenciesView() -> some View {
        VStack(alignment: .leading, spacing: 8) {
            ForEach(currencies, id: \.self) { currency in
                CurrencyItemView(currency: currency)
            }
            
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 24)
    }
    
    @ViewBuilder private func CurrencyItemView(currency: Currency) -> some View {
        let isSelected = selectedCurrency == currency
        HStack(alignment: .center) {
            VStack(alignment: .leading, spacing: 8) {
                Text(currency.name).font(Typography.titleMedium)
                Text(currency.countryName).font(Typography.bodyMedium).foregroundStyle(ColorTheme.secondary)
            }
            Spacer()
            OutlinedCheckmarkRadioButton(selected: isSelected, onClick: { selectedCurrency = currency})
        }
        .padding(.vertical, 4)
        .padding(.horizontal, 8)
        .frame(maxWidth: .infinity, alignment: .leading)
        .customBackground(backgroundColor: ColorTheme.surface)
        .onTapGesture {selectedCurrency = currency }
    }
}

#Preview {
    SelectCurrencyContentView()
}
