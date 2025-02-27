import SwiftUI

struct DeleteContentDialogView: View {
    
    let title: String
    let subtitle: String
    let onDismiss: () -> Void
    let onConfirm: () -> Void
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(title)
                .font(Typography.headlineSmall)
            Spacer()
                .frame(height: 16)
            Text(subtitle)
                .font(Typography.bodyMedium)
            Spacer()
                .frame(height: 24)
            HStack(spacing: 16) {
                Button(action: onDismiss) {
                    Text("Cancel")
                        .font(Typography.labelLarge)
                        .foregroundStyle(ColorTheme.primary)
                }
                .buttonStyle(PlainButtonStyle())
                .frame(width: 120,height: 48)
                
                FilledButtonView(
                    text: "Delete",
                    bgColor: ColorTheme.error,
                    onClick: onConfirm
                )
                .frame(width: 120,height: 48)
            }
            .frame(maxWidth: .infinity, alignment: .trailing)
        }
    }
}

#Preview {
    DeleteContentDialogView(
        title: "Are you sure want to delete this Goal ?",
        subtitle: "This action cannot be undone.",
        onDismiss: {},
        onConfirm: {}
    )
}
