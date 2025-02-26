import SwiftUI
import Shared

struct WalletScreen: View {
    
    private var filterColorIndicator: Color {
        ColorTheme.outline
    }
    
    var body: some View {
        VStack {
            WalletTopbar()
            WalletContent()
            Spacer()
        }
        .background(ColorTheme.surface.ignoresSafeArea())
    }
    
    @ViewBuilder
    private func WalletTopbar() -> some View {
        VStack {
            NavigationTopAppbar(
                title: "Wallet",
                actionContent: {
                    BarAction(systemName: "pencil")
                    Spacer().frame(width: 16)
                    BarAction(systemName: "trash")
                },
                navigateBack: {}
            )
            Divider()
        }
    }
    
    @ViewBuilder
    private func BarAction(systemName: String) -> some View {
        Image(systemName: systemName)
            .resizable()
            .frame(width: 24, height: 24)
    }
    
    @ViewBuilder
    private func WalletContent() -> some View {
        VStack(alignment: .leading, spacing: 16) {
            WalletDetailItemView(state: WalletState.companion.default())
            CardNoteView(note: "Hello world!")
            FilterLogView()
        }
        .padding(.horizontal, 16)
        .padding(.top, 24)
    }
    
    @ViewBuilder
    private func FilterLogView() -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Wallet History")
            FilterLogComponent()
        }
    }
    
    @ViewBuilder
    private func FilterLogComponent() -> some View {
        HStack(alignment: .center, spacing: 8) {
            OutlinedSearchTextFieldView(query: .constant("Hello"), placeHolder: "Search")
            Image(systemName: "line.3.horizontal.decrease")
                .resizable()
                .scaledToFit()
                .foregroundStyle(filterColorIndicator)
                .frame(width: 24, height: 24)
        }
    }
}

#Preview {
    WalletScreen()
}
