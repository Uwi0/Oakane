import SwiftUI
import Shared

extension String {
    func asIconCategory() -> String {
        switch self {
        case CategoryName.shared.SALARY:
            return IconConstant.BriefcaseFill
        case CategoryName.shared.FREELANCE:
            return IconConstant.PersonCropCircleBadgeCheckmark
        case CategoryName.shared.BUSINESS_PROFITS:
            return IconConstant.ChartBarXAxis
        case CategoryName.shared.RENTAL_INCOME:
            return IconConstant.Building2CropCircle
        case CategoryName.shared.INTEREST:
            return IconConstant.Percent
        case CategoryName.shared.DIVIDENDS:
            return IconConstant.ChartLineUptrendXYAxis
        case CategoryName.shared.GIFTS:
            return IconConstant.GiftFill

        case CategoryName.shared.RENT_MORTGAGE:
            return IconConstant.BuildingColumnsFill
        case CategoryName.shared.UTILITIES:
            return IconConstant.BoltFill
        case CategoryName.shared.HOME_INSURANCE:
            return IconConstant.ShieldFill
        case CategoryName.shared.PROPERTY_TAXES:
            return IconConstant.BanknoteFill
        case CategoryName.shared.HOME_MAINTENANCE:
            return IconConstant.HammerFill
        case CategoryName.shared.HOA_FEES:
            return IconConstant.BanknoteFill

        case CategoryName.shared.CAR_PAYMENT:
            return IconConstant.CarFill
        case CategoryName.shared.GAS:
            return IconConstant.FuelpumpFill
        case CategoryName.shared.INSURANCE:
            return IconConstant.ShieldFill
        case CategoryName.shared.PARKING_FEES:
            return IconConstant.ParkingFill
        case CategoryName.shared.PUBLIC_TRANSPORTATION:
            return IconConstant.BusFill
        case CategoryName.shared.MAINTENANCE:
            return IconConstant.WrenchFill

        case CategoryName.shared.GROCERIES:
            return IconConstant.CartFill
        case CategoryName.shared.RESTAURANTS:
            return IconConstant.ForkKnife
        case CategoryName.shared.DINING_OUT:
            return IconConstant.ForkKnifeCircle

        case CategoryName.shared.MOVIES:
            return IconConstant.FilmFill
        case CategoryName.shared.CONCERTS:
            return IconConstant.SpeakerFill
        case CategoryName.shared.SUBSCRIPTIONS:
            return IconConstant.Calendar
        case CategoryName.shared.HOBBIES:
            return IconConstant.GamecontrollerFill
        case CategoryName.shared.TRAVEL:
            return IconConstant.MapFill

        case CategoryName.shared.TUITION:
            return IconConstant.GraduationCapFill
        case CategoryName.shared.BOOKS:
            return IconConstant.BookFill
        case CategoryName.shared.SUPPLIES:
            return IconConstant.TrayFullFill

        case CategoryName.shared.DOCTORS_VISITS:
            return IconConstant.Stethoscope
        case CategoryName.shared.PRESCRIPTIONS:
            return IconConstant.PillsFill
        case CategoryName.shared.INSURANCE_PREMIUMS:
            return IconConstant.ShieldCheckerboard
        case CategoryName.shared.DENTAL_CARE:
            return IconConstant.FaceSmilingFill
        case CategoryName.shared.VISION_CARE:
            return IconConstant.Eyeglasses

        case CategoryName.shared.HAIR:
            return IconConstant.CombFill
        case CategoryName.shared.NAILS:
            return IconConstant.HandThumbsupFill
        case CategoryName.shared.CLOTHING:
            return IconConstant.TShirt
        case CategoryName.shared.TOILETRIES:
            return IconConstant.Shower

        case CategoryName.shared.GIFTS_EXPENSE:
            return IconConstant.Gift
        case CategoryName.shared.CLOTHES:
            return IconConstant.BagFill
        case CategoryName.shared.ELECTRONICS:
            return IconConstant.DesktopComputer
        case CategoryName.shared.HOUSEHOLD_ITEMS:
            return IconConstant.HouseFill

        case CategoryName.shared.CREDIT_CARD_PAYMENTS:
            return IconConstant.CreditCard
        case CategoryName.shared.LOAN_PAYMENTS:
            return IconConstant.BanknoteFill
        case CategoryName.shared.INTEREST_EXPENSE:
            return IconConstant.ArrowUpRightCircle

        default:
            return IconConstant.AppleLogo
        }
    }
}
