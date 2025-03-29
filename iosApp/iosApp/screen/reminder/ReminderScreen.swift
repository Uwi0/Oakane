import SwiftUI
import Shared

struct ReminderScreen: View {
    
    @EnvironmentObject private var navigation: AppNavigation
    @StateObject private var viewModel: ReminderViewModel = ReminderViewModel()
    
    var body: some View {
        VStack {
            ReminderTopAppBar()
            VStack(alignment: .leading, spacing: 16) {
                Toggle(
                    "Switch Reminder",
                    isOn: Binding(
                        get: { viewModel.uiState.enabledReminder },
                        set: { viewModel.handle(event: .ToggleReminder(enabled: $0)) }
                    )
                )
                Divider()
                DatePicker(
                    "Select Time",
                    selection: Binding(
                        get: { viewModel.uiState.toDate() },
                        set: { date in viewModel.handle(event: date.toTimeSelected()) }
                    ),
                    displayedComponents: .hourAndMinute
                )
                SelectedDayView()
                RecurringDayView()
                Divider()
                Spacer()
                FilledContentButtonView(
                    onclick: { viewModel.handle(event: .SaveReminder()) },
                    content: { Text("Set Reminder") }
                )
            }
            .padding(.vertical, 24)
            .padding(.horizontal, 16)
        }
        .background(ColorTheme.surface)
        .navigationBarBackButtonHidden(true)
        .task {
            viewModel.initViewModel()
        }
        .onChange(of: viewModel.uiEffect) {
            observeEffect(effect: viewModel.uiEffect)
        }
    }
    
    @ViewBuilder
    private func ReminderTopAppBar() -> some View {
        VStack {
            NavigationTopAppbar(
                title: "Reminder",
                onAction: { viewModel.handle(event: .NavigateUp())}
            )
            Divider()
        }
    }
    
    @ViewBuilder
    private func SelectedDayView() -> some View {
        HStack(spacing: 4) {
            Text("Selected Days : ")
            ForEach(viewModel.uiState.selectedDays, id: \.ordinal) { day in
                Text(day.title).font(Typography.bodyMedium)
            }
        }
    }
    
    @ViewBuilder
    private func RecurringDayView() -> some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack {
                ForEach(ReminderDay.allCases, id: \.ordinal) { day in
                    let selected = viewModel.uiState.selectedDays.contains(day)
                    ButtonDayReminder(
                        day: day,
                        selected: selected,
                        onClick: { viewModel.handle(event: .DaySelected(day: day))}
                    )
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
    
    private func observeEffect(effect: ReminderEffect?) {
        guard let effect = effect else { return }
        switch onEnum(of: effect) {
        case .createdReminder(let reminder): print("reminder created: \(reminder)")
        case .navigateBack: navigation.navigateBack()
        case .showError(let effect): print("error \(effect.message)")
        }
        viewModel.uiEffect = nil
    }
}

#Preview {
    ReminderScreen()
}
