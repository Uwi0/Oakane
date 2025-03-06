import Foundation
import SwiftUICore

struct GithubIcon: Shape {
    func path(in rect: CGRect) -> Path {
        var path = Path()
        let width = rect.size.width
        let height = rect.size.height
        path.move(to: CGPoint(x: 0.5*width, y: 0.01238*height))
        path.addCurve(to: CGPoint(x: 0, y: 0.51238*height), control1: CGPoint(x: 0.22378*width, y: 0.01238*height), control2: CGPoint(x: 0, y: 0.23634*height))
        path.addCurve(to: CGPoint(x: 0.34178*width, y: 0.98684*height), control1: CGPoint(x: 0, y: 0.73341*height), control2: CGPoint(x: 0.14325*width, y: 0.92075*height))
        path.addCurve(to: CGPoint(x: 0.37594*width, y: 0.96272*height), control1: CGPoint(x: 0.36688*width, y: 0.99156*height), control2: CGPoint(x: 0.37594*width, y: 0.97609*height))
        path.addCurve(to: CGPoint(x: 0.37547*width, y: 0.87775*height), control1: CGPoint(x: 0.37594*width, y: 0.95084*height), control2: CGPoint(x: 0.37566*width, y: 0.91944*height))
        path.addCurve(to: CGPoint(x: 0.207*width, y: 0.81053*height), control1: CGPoint(x: 0.23631*width, y: 0.90787*height), control2: CGPoint(x: 0.207*width, y: 0.81053*height))
        path.addCurve(to: CGPoint(x: 0.15134*width, y: 0.73744*height), control1: CGPoint(x: 0.18422*width, y: 0.75291*height), control2: CGPoint(x: 0.15134*width, y: 0.73744*height))
        path.addCurve(to: CGPoint(x: 0.15494*width, y: 0.70719*height), control1: CGPoint(x: 0.10609*width, y: 0.70653*height), control2: CGPoint(x: 0.15494*width, y: 0.70719*height))
        path.addCurve(to: CGPoint(x: 0.23144*width, y: 0.75859*height), control1: CGPoint(x: 0.20506*width, y: 0.71059*height), control2: CGPoint(x: 0.23144*width, y: 0.75859*height))
        path.addCurve(to: CGPoint(x: 0.37709*width, y: 0.80009*height), control1: CGPoint(x: 0.27603*width, y: 0.83509*height), control2: CGPoint(x: 0.34844*width, y: 0.81297*height))
        path.addCurve(to: CGPoint(x: 0.40869*width, y: 0.73338*height), control1: CGPoint(x: 0.38147*width, y: 0.76788*height), control2: CGPoint(x: 0.3945*width, y: 0.74572*height))
        path.addCurve(to: CGPoint(x: 0.18097*width, y: 0.48628*height), control1: CGPoint(x: 0.29769*width, y: 0.72084*height), control2: CGPoint(x: 0.18097*width, y: 0.67788*height))
        path.addCurve(to: CGPoint(x: 0.23237*width, y: 0.352*height), control1: CGPoint(x: 0.18097*width, y: 0.43156*height), control2: CGPoint(x: 0.20034*width, y: 0.387*height))
        path.addCurve(to: CGPoint(x: 0.23678*width, y: 0.21966*height), control1: CGPoint(x: 0.22684*width, y: 0.33947*height), control2: CGPoint(x: 0.20991*width, y: 0.28853*height))
        path.addCurve(to: CGPoint(x: 0.37428*width, y: 0.27094*height), control1: CGPoint(x: 0.23678*width, y: 0.21966*height), control2: CGPoint(x: 0.27862*width, y: 0.20631*height))
        path.addCurve(to: CGPoint(x: 0.49928*width, y: 0.25403*height), control1: CGPoint(x: 0.41431*width, y: 0.25984*height), control2: CGPoint(x: 0.45681*width, y: 0.25431*height))
        path.addCurve(to: CGPoint(x: 0.62428*width, y: 0.27094*height), control1: CGPoint(x: 0.54178*width, y: 0.25431*height), control2: CGPoint(x: 0.58425*width, y: 0.25988*height))
        path.addCurve(to: CGPoint(x: 0.76119*width, y: 0.21966*height), control1: CGPoint(x: 0.71937*width, y: 0.20631*height), control2: CGPoint(x: 0.76119*width, y: 0.21966*height))
        path.addCurve(to: CGPoint(x: 0.76622*width, y: 0.352*height), control1: CGPoint(x: 0.78803*width, y: 0.28853*height), control2: CGPoint(x: 0.77109*width, y: 0.33944*height))
        path.addCurve(to: CGPoint(x: 0.81731*width, y: 0.48628*height), control1: CGPoint(x: 0.79794*width, y: 0.387*height), control2: CGPoint(x: 0.81731*width, y: 0.43159*height))
        path.addCurve(to: CGPoint(x: 0.58931*width, y: 0.73288*height), control1: CGPoint(x: 0.81731*width, y: 0.67831*height), control2: CGPoint(x: 0.70044*width, y: 0.72066*height))
        path.addCurve(to: CGPoint(x: 0.62297*width, y: 0.82534*height), control1: CGPoint(x: 0.60669*width, y: 0.74784*height), control2: CGPoint(x: 0.62297*width, y: 0.77863*height))
        path.addCurve(to: CGPoint(x: 0.62234*width, y: 0.96238*height), control1: CGPoint(x: 0.62297*width, y: 0.89222*height), control2: CGPoint(x: 0.62234*width, y: 0.94609*height))
        path.addCurve(to: CGPoint(x: 0.65684*width, y: 0.98597*height), control1: CGPoint(x: 0.62234*width, y: 0.97538*height), control2: CGPoint(x: 0.63109*width, y: 0.991*height))
        path.addCurve(to: CGPoint(x: 0.99994*width, y: 0.51234*height), control1: CGPoint(x: 0.85684*width, y: 0.92056*height), control2: CGPoint(x: 0.99994*width, y: 0.73306*height))
        path.addCurve(to: CGPoint(x: 0.49994*width, y: 0.01234*height), control1: CGPoint(x: 0.99994*width, y: 0.23631*height), control2: CGPoint(x: 0.77616*width, y: 0.01234*height))
        path.closeSubpath()
        return path
    }
}
