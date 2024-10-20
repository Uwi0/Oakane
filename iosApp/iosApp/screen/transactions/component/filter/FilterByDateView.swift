import SwiftUI

struct FilterByDateView: View {
    @State private var date: Date = Date()
    var body: some View {
        DatePicker("Date", selection: $date)
    }
}

#Preview {
    FilterByDateView()
}
