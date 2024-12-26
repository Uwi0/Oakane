import SwiftUI
import Shared

struct ButtonFilterReportView: View {
    
    let wallets: [WalletItemModel]
    let onEvent: (ReportsEvent) -> Void
    
    @State private var selectedMonth: Int = Calendar.current.component(.month, from: Date())
    @State private var selectedWallet: WalletItemModel = defaultWalletItem
    private let months = Calendar.current.monthSymbols
    
    private var formattedMonth: MonthReport {
        Int32(selectedMonth).toMonthReport()
    }
    
    var body: some View {
        HStack {
            FilterDatePicker().onChange(of: selectedMonth) {
                onEvent(.FilterBy(month: formattedMonth))
            }
            FilterWalletPicker().onChange(of: selectedWallet) {
                onEvent(.Selected(wallet: selectedWallet))
            }
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
    
    @ViewBuilder private func FilterDatePicker() -> some View {
        Picker("Select Month", selection: $selectedMonth) {
            ForEach(1...12, id: \.self) { month in
                Text(months[month - 1]).tag(month)
            }
            .frame(maxHeight: 380)
        }
        .pickerStyle(MenuPickerStyle())
        .tint(ColorTheme.outline)
        .background(RoundedRectangle(cornerRadius: 8).fill(ColorTheme.surface).shadow(radius: 2))
    }
    
    @ViewBuilder private func FilterWalletPicker() -> some View {
        Picker("Select Wallet", selection: $selectedWallet) {
            ForEach(wallets, id: \.id) { wallet in
                Text(wallet.name).tag(wallet)
            }
            .frame(maxHeight: 380)
        }
        .pickerStyle(MenuPickerStyle())
        .tint(ColorTheme.outline)
        .background(RoundedRectangle(cornerRadius: 8).fill(ColorTheme.surface).shadow(radius: 2))
    }
}
