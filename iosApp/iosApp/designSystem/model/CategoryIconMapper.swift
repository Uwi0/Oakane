import SwiftUI
import Shared

extension String {
    func asIconCategory() -> (icon: String, color: Color) {
        switch self {
        case CategoryName.shared.SALARY:
            return (IconConstant.BriefcaseFill, CategoryColor.salary)
        case CategoryName.shared.FREELANCE:
            return (IconConstant.PersonCropCircleBadgeCheckmark, CategoryColor.freelance)
        case CategoryName.shared.BUSINESS_PROFITS:
            return (IconConstant.ChartBarXAxis, CategoryColor.businessProfits)
        case CategoryName.shared.RENTAL_INCOME:
            return (IconConstant.Building2CropCircle, CategoryColor.rentalIncome)
        case CategoryName.shared.INTEREST:
            return (IconConstant.Percent, CategoryColor.interest)
        case CategoryName.shared.DIVIDENDS:
            return (IconConstant.ChartLineUptrendXYAxis, CategoryColor.dividends)
        case CategoryName.shared.GIFTS:
            return (IconConstant.GiftFill, CategoryColor.gifts)

        case CategoryName.shared.RENT_MORTGAGE:
            return (IconConstant.BuildingColumnsFill, CategoryColor.rentMortgage)
        case CategoryName.shared.UTILITIES:
            return (IconConstant.BoltFill, CategoryColor.utilities)
        case CategoryName.shared.HOME_INSURANCE:
            return (IconConstant.ShieldFill, CategoryColor.homeInsurance)
        case CategoryName.shared.PROPERTY_TAXES:
            return (IconConstant.BanknoteFill, CategoryColor.propertyTaxes)
        case CategoryName.shared.HOME_MAINTENANCE:
            return (IconConstant.HammerFill, CategoryColor.homeMaintenance)
        case CategoryName.shared.HOA_FEES:
            return (IconConstant.BanknoteFill, CategoryColor.hoaFees)

        case CategoryName.shared.CAR_PAYMENT:
            return (IconConstant.CarFill, CategoryColor.carPayment)
        case CategoryName.shared.GAS:
            return (IconConstant.FuelpumpFill, CategoryColor.gas)
        case CategoryName.shared.INSURANCE:
            return (IconConstant.ShieldFill, CategoryColor.insurance)
        case CategoryName.shared.PARKING_FEES:
            return (IconConstant.ParkingFill, CategoryColor.parkingFees)
        case CategoryName.shared.PUBLIC_TRANSPORTATION:
            return (IconConstant.BusFill, CategoryColor.publicTransportation)
        case CategoryName.shared.MAINTENANCE:
            return (IconConstant.WrenchFill, CategoryColor.maintenance)

        case CategoryName.shared.GROCERIES:
            return (IconConstant.CartFill, CategoryColor.groceries)
        case CategoryName.shared.RESTAURANTS:
            return (IconConstant.ForkKnife, CategoryColor.restaurants)
        case CategoryName.shared.DINING_OUT:
            return (IconConstant.ForkKnifeCircle, CategoryColor.diningOut)

        case CategoryName.shared.MOVIES:
            return (IconConstant.FilmFill, CategoryColor.movies)
        case CategoryName.shared.CONCERTS:
            return (IconConstant.SpeakerFill, CategoryColor.concerts)
        case CategoryName.shared.SUBSCRIPTIONS:
            return (IconConstant.Calendar, CategoryColor.subscriptions)
        case CategoryName.shared.HOBBIES:
            return (IconConstant.GamecontrollerFill, CategoryColor.hobbies)
        case CategoryName.shared.TRAVEL:
            return (IconConstant.MapFill, CategoryColor.travel)

        case CategoryName.shared.TUITION:
            return (IconConstant.GraduationCapFill, CategoryColor.tuition)
        case CategoryName.shared.BOOKS:
            return (IconConstant.BookFill, CategoryColor.books)
        case CategoryName.shared.SUPPLIES:
            return (IconConstant.TrayFullFill, CategoryColor.supplies)

        case CategoryName.shared.DOCTORS_VISITS:
            return (IconConstant.Stethoscope, CategoryColor.doctorsVisits)
        case CategoryName.shared.PRESCRIPTIONS:
            return (IconConstant.PillsFill, CategoryColor.prescriptions)
        case CategoryName.shared.INSURANCE_PREMIUMS:
            return (IconConstant.ShieldCheckerboard, CategoryColor.insurancePremiums)
        case CategoryName.shared.DENTAL_CARE:
            return (IconConstant.FaceSmilingFill, CategoryColor.dentalCare)
        case CategoryName.shared.VISION_CARE:
            return (IconConstant.Eyeglasses, CategoryColor.visionCare)

        case CategoryName.shared.HAIR:
            return (IconConstant.CombFill, CategoryColor.hair)
        case CategoryName.shared.NAILS:
            return (IconConstant.HandThumbsupFill, CategoryColor.nails)
        case CategoryName.shared.CLOTHING:
            return (IconConstant.TShirt, CategoryColor.clothing)
        case CategoryName.shared.TOILETRIES:
            return (IconConstant.Shower, CategoryColor.toiletries)

        case CategoryName.shared.GIFTS_EXPENSE:
            return (IconConstant.Gift, CategoryColor.giftsExpense)
        case CategoryName.shared.CLOTHES:
            return (IconConstant.BagFill, CategoryColor.clothes)
        case CategoryName.shared.ELECTRONICS:
            return (IconConstant.DesktopComputer, CategoryColor.electronics)
        case CategoryName.shared.HOUSEHOLD_ITEMS:
            return (IconConstant.HouseFill, CategoryColor.householdItems)

        case CategoryName.shared.CREDIT_CARD_PAYMENTS:
            return (IconConstant.CreditCard, CategoryColor.creditCardPayments)
        case CategoryName.shared.LOAN_PAYMENTS:
            return (IconConstant.BanknoteFill, CategoryColor.loanPayments)
        case CategoryName.shared.INTEREST_EXPENSE:
            return (IconConstant.ArrowUpRightCircle, CategoryColor.interestExpense)

        default:
            return (IconConstant.AppleLogo, Color.secondary)
        }
    }
}
