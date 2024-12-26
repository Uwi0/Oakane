import SwiftUI
import Charts

internal struct DonutChartComponentView: View {
    
    let reports: [ReportModel]
    let walletBalance: Double
    
    var body: some View {
        VStack(spacing: 16) {
            Chart(reports){ report in
                SectorMark(
                    angle: .value("Percent", report.proportion),
                    innerRadius: .ratio(0.65),
                    angularInset: 0
                )
                .foregroundStyle(Color(hex: report.color))
            }
            .frame(height: 280)
            .scaledToFit()
            .overlay(alignment: .center){
                VStack(spacing: 8) {
                    Text(walletBalance.toIDRCurrency())
                        .font(Typography.titleMedium)
                    Text("Total Balance")
                        .font(Typography.bodyMedium)
                        .foregroundStyle(ColorTheme.outline)
                }
            }
            
            legendContentView()
            
        }
        .customBackground(backgroundColor: ColorTheme.surface)
    }
    
    @ViewBuilder func legendContentView() -> some View {
        let minimum: CGFloat = 80
        let columns = [
            GridItem(.flexible(minimum: minimum)),
            GridItem(.flexible(minimum: minimum)),
            GridItem(.flexible(minimum: minimum)),
        ]
        
        LazyVGrid (columns: columns, alignment: .leading) {
            ForEach(reports) { report in
                legendMarkView(report: report)
            }
        }
    }
    
    @ViewBuilder func legendMarkView(report: ReportModel) -> some View {
        HStack(alignment: .center, spacing: 8) {
            Circle().frame(width: 8, height: 8).foregroundStyle(Color(hex: report.color))
            Text(report.title).font(.caption2)
                .lineLimit(1)
                .foregroundStyle(ColorTheme.outline)
        }
    }
}
