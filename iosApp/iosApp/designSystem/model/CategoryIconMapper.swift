import SwiftUI
import Shared

extension CategoryIconName {
    func asIconCategory() -> String {
        switch self {
        case .salary:
            return IconConstant.BriefcaseFill
        case .freelance:
            return IconConstant.PersonCropCircleBadgeCheckmark
        case .businessProfits:
            return IconConstant.ChartBarXAxis
        case .rentalIncome:
            return IconConstant.Building2CropCircle
        case .interest:
            return IconConstant.Percent
        case .dividends:
            return IconConstant.ChartLineUptrendXYAxis
        case .gifts:
            return IconConstant.GiftFill
        case .wallet:
            return IconConstant.WalletBifold
            
        case .rentMortgage:
            return IconConstant.BuildingColumnsFill
        case .utilities:
            return IconConstant.BoltFill
        case .homeInsurance:
            return IconConstant.ShieldFill
        case .propertyTaxes:
            return IconConstant.BanknoteFill
        case .homeMaintenance:
            return IconConstant.HammerFill
        case .hoaFees:
            return IconConstant.BanknoteFill
            
        case .carPayment:
            return IconConstant.CarFill
        case .gas:
            return IconConstant.FuelpumpFill
        case .insurance:
            return IconConstant.ShieldFill
        case .parkingFees:
            return IconConstant.ParkingFill
        case .publicTransportation:
            return IconConstant.BusFill
        case .maintenance:
            return IconConstant.WrenchFill
            
        case .groceries:
            return IconConstant.CartFill
        case .restaurants:
            return IconConstant.ForkKnife
        case .diningOut:
            return IconConstant.ForkKnifeCircle
            
        case .movies:
            return IconConstant.FilmFill
        case .concerts:
            return IconConstant.SpeakerFill
        case .subscriptions:
            return IconConstant.Calendar
        case .hobbies:
            return IconConstant.GamecontrollerFill
        case .travel:
            return IconConstant.MapFill
            
        case .tuition:
            return IconConstant.GraduationCapFill
        case .books:
            return IconConstant.BookFill
        case .supplies:
            return IconConstant.TrayFullFill
            
        case .doctorsVisits:
            return IconConstant.Stethoscope
        case .prescriptions:
            return IconConstant.PillsFill
        case .insurancePremiums:
            return IconConstant.ShieldCheckerboard
        case .dentalCare:
            return IconConstant.FaceSmilingFill
        case .visionCare:
            return IconConstant.Eyeglasses
            
        case .hair:
            return IconConstant.CombFill
        case .nails:
            return IconConstant.HandThumbsupFill
        case .clothing:
            return IconConstant.TShirt
        case .toiletries:
            return IconConstant.Shower
            
        case .giftsExpense:
            return IconConstant.Gift
        case .clothes:
            return IconConstant.BagFill
        case .electronics:
            return IconConstant.DesktopComputer
        case .householdItems:
            return IconConstant.HouseFill
            
        case .creditCardPayments:
            return IconConstant.CreditCard
        case .loanPayments:
            return IconConstant.BanknoteFill
        case .interestExpense:
            return IconConstant.ArrowUpRightCircle
        case .default:
            return IconConstant.AppleLogo
        }
    }
}
