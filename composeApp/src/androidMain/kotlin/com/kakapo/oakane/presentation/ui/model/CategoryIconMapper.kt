package com.kakapo.oakane.presentation.ui.model

import androidx.compose.ui.graphics.Color
import com.kakapo.oakane.R
import com.kakapo.oakane.model.category.CategoryName

fun String.asIconCategory() : Pair<Int, Color> {
    return when (this) {
        // INCOME
        CategoryName.SALARY -> Pair(R.drawable.ic_rounded_work, CategoryColor.SALARY)
        CategoryName.FREELANCE -> Pair(R.drawable.ic_rounded_home_work, CategoryColor.FREELANCE)
        CategoryName.BUSINESS_PROFITS -> Pair(R.drawable.ic_rounded_trending_up, CategoryColor.BUSINESS_PROFITS)
        CategoryName.RENTAL_INCOME -> Pair(R.drawable.ic_outline_insights, CategoryColor.RENTAL_INCOME)
        CategoryName.INTEREST -> Pair(R.drawable.ic_round_attach_money, CategoryColor.INTEREST)
        CategoryName.DIVIDENDS -> Pair(R.drawable.ic_rounded_show_chart, CategoryColor.DIVIDENDS)
        CategoryName.GIFTS -> Pair(R.drawable.ic_outline_card_giftcard, CategoryColor.GIFTS)

        // HOUSING
        CategoryName.RENT_MORTGAGE -> Pair(R.drawable.ic_rounded_real_estate_agent, CategoryColor.RENT_MORTGAGE)
        CategoryName.UTILITIES -> Pair(R.drawable.ic_outline_lightbulb, CategoryColor.UTILITIES)
        CategoryName.HOME_INSURANCE -> Pair(R.drawable.ic_outline_assignment, CategoryColor.HOME_INSURANCE)
        CategoryName.PROPERTY_TAXES -> Pair(R.drawable.ic_round_attach_money, CategoryColor.PROPERTY_TAXES)
        CategoryName.HOME_MAINTENANCE -> Pair(R.drawable.ic_outline_maintenance, CategoryColor.HOME_MAINTENANCE)
        CategoryName.HOA_FEES -> Pair(R.drawable.ic_outline_home, CategoryColor.HOA_FEES)

        // TRANSPORTATION
        CategoryName.CAR_PAYMENT -> Pair(R.drawable.ic_outline_payments, CategoryColor.CAR_PAYMENT)
        CategoryName.GAS -> Pair(R.drawable.ic_outline_gas, CategoryColor.GAS)
        CategoryName.INSURANCE -> Pair(R.drawable.ic_outline_transportation_insurance, CategoryColor.INSURANCE)
        CategoryName.PARKING_FEES -> Pair(R.drawable.ic_outline_local_parking, CategoryColor.PARKING_FEES)
        CategoryName.PUBLIC_TRANSPORTATION -> Pair(R.drawable.ic_outline_directions_bus, CategoryColor.PUBLIC_TRANSPORTATION)
        CategoryName.MAINTENANCE -> Pair(R.drawable.ic_outline_handyman, CategoryColor.MAINTENANCE)

        // FOOD
        CategoryName.GROCERIES -> Pair(R.drawable.ic_outline_local_grocery_store, CategoryColor.GROCERIES)
        CategoryName.RESTAURANTS -> Pair(R.drawable.ic_outline_restaurant_menu, CategoryColor.RESTAURANTS)
        CategoryName.DINING_OUT -> Pair(R.drawable.ic_outline_fastfood, CategoryColor.DINING_OUT)

        // ENTERTAINMENT
        CategoryName.MOVIES -> Pair(R.drawable.ic_outline_movie, CategoryColor.MOVIES)
        CategoryName.CONCERTS -> Pair(R.drawable.ic_rounded_event, CategoryColor.CONCERTS)
        CategoryName.SUBSCRIPTIONS -> Pair(R.drawable.ic_baseline_subscriptions, CategoryColor.SUBSCRIPTIONS)
        CategoryName.HOBBIES -> Pair(R.drawable.ic_outline_emoji_events, CategoryColor.HOBBIES)
        CategoryName.TRAVEL -> Pair(R.drawable.ic_outline_airplanemode_active, CategoryColor.TRAVEL)

        // EDUCATION
        CategoryName.TUITION -> Pair(R.drawable.ic_outline_school, CategoryColor.TUITION)
        CategoryName.BOOKS -> Pair(R.drawable.ic_outline_book, CategoryColor.BOOKS)
        CategoryName.SUPPLIES -> Pair(R.drawable.ic_outline_inventory_2, CategoryColor.SUPPLIES)

        // HEALTHCARE
        CategoryName.DOCTORS_VISITS -> Pair(R.drawable.ic_rounded_calendar_today, CategoryColor.DOCTORS_VISITS)
        CategoryName.PRESCRIPTIONS -> Pair(R.drawable.ic_rounded_local_pharmacy, CategoryColor.PRESCRIPTIONS)
        CategoryName.INSURANCE_PREMIUMS -> Pair(R.drawable.ic_outline_monetization_on, CategoryColor.INSURANCE_PREMIUMS)
        CategoryName.DENTAL_CARE -> Pair(R.drawable.ic_rounded_health_and_safety, CategoryColor.DENTAL_CARE)
        CategoryName.VISION_CARE -> Pair(R.drawable.ic_outline_remove_red_eye, CategoryColor.VISION_CARE)

        // PERSONAL CARE
        CategoryName.HAIR -> Pair(R.drawable.ic_outline_face_retouching, CategoryColor.HAIR)
        CategoryName.NAILS -> Pair(R.drawable.ic_rounded_spa, CategoryColor.NAILS)
        CategoryName.CLOTHING -> Pair(R.drawable.ic_round_checkroom, CategoryColor.CLOTHING)
        CategoryName.TOILETRIES -> Pair(R.drawable.ic_rounded_soap, CategoryColor.TOILETRIES)

        // SHOPPING
        CategoryName.GIFTS_EXPENSE -> Pair(R.drawable.ic_rounded_celebration, CategoryColor.GIFTS_EXPENSE)
        CategoryName.CLOTHES -> Pair(R.drawable.ic_round_checkroom, CategoryColor.CLOTHES)
        CategoryName.ELECTRONICS -> Pair(R.drawable.ic_rounded_devices, CategoryColor.ELECTRONICS)
        CategoryName.HOUSEHOLD_ITEMS -> Pair(R.drawable.ic_rounded_weekend, CategoryColor.HOUSEHOLD_ITEMS)

        // DEBT
        CategoryName.CREDIT_CARD_PAYMENTS -> Pair(R.drawable.ic_rounded_credit_card, CategoryColor.CREDIT_CARD_PAYMENTS)
        CategoryName.LOAN_PAYMENTS -> Pair(R.drawable.ic_outline_payments, CategoryColor.LOAN_PAYMENTS)
        CategoryName.INTEREST_EXPENSE -> Pair(R.drawable.ic_round_attach_money, CategoryColor.INTEREST_EXPENSE)

        else -> Pair(R.drawable.fubuki_stare, Color.Gray) // Default case
    }
}