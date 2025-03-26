import SwiftUI
import Shared

struct ReminderScreen: View {
    
    @State private var selectedTime: Date = Date()
    var body: some View {
        VStack {
            ReminderTopAppBar()
            VStack(spacing: 16) {
                Toggle("Switch Reminder", isOn: .constant(true))
                Divider()
                DatePicker("Select Time", selection: $selectedTime, displayedComponents: .hourAndMinute)
                RecurringDayView()
                Divider()
                Spacer()
                FilledContentButtonView(
                    onclick: {},
                    content: { Text("Set Reminder") }
                )
            }
            .padding(.vertical, 24)
            .padding(.horizontal, 16)
        }
        .background(ColorTheme.surface)
    }
    
    @ViewBuilder
    private func ReminderTopAppBar() -> some View {
        VStack {
            NavigationTopAppbar(title: "Reminder", onAction: {})
            Divider()
        }
    }
    
    @ViewBuilder
    private func RecurringDayView() -> some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack {
                ForEach(ReminderDay.allCases, id: \.ordinal) { day in
                    ButtonDayReminder(day: day, selected: day == ReminderDay.monday, onClick: {})
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
        }
    }
    
    @ViewBuilder
    private func ButtonDayReminder(day: ReminderDay, selected: Bool, onClick: @escaping () -> Void) -> some View {
        Button(action: onClick) {
            Text(day.title)
                .font(Typography.titleMedium)
                .padding(.horizontal, 12)
                .padding(.vertical, 8)
                .background(selected ? ColorTheme.secondaryContainer : ColorTheme.surface)
                .foregroundStyle(selected ? ColorTheme.primary: ColorTheme.secondary)
                .clipShape(RoundedRectangle(cornerRadius: 8))
                .overlay(
                    RoundedRectangle(cornerRadius: 8)
                        .stroke(selected ? ColorTheme.secondary : ColorTheme.outline, lineWidth: 1)
                )
        }
        .buttonStyle(PlainButtonStyle())
        
    }
}

#Preview {
    ReminderScreen()
}
