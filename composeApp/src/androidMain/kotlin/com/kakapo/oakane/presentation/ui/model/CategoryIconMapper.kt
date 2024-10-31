package com.kakapo.oakane.presentation.ui.model

import androidx.compose.ui.graphics.Color
import com.kakapo.oakane.R
import com.kakapo.oakane.model.category.CategoryName

fun String.asIconCategory(): Int {
    return when (this) {
        // INCOME
        CategoryName.SALARY -> R.drawable.ic_rounded_work
        CategoryName.FREELANCE -> R.drawable.ic_rounded_home_work
        CategoryName.BUSINESS_PROFITS -> R.drawable.ic_rounded_trending_up
        CategoryName.RENTAL_INCOME -> R.drawable.ic_outline_insights
        CategoryName.INTEREST -> R.drawable.ic_round_attach_money
        CategoryName.DIVIDENDS -> R.drawable.ic_rounded_show_chart
        CategoryName.GIFTS -> R.drawable.ic_outline_card_giftcard

        // HOUSING
        CategoryName.RENT_MORTGAGE -> R.drawable.ic_rounded_real_estate_agent
        CategoryName.UTILITIES -> R.drawable.ic_outline_lightbulb
        CategoryName.HOME_INSURANCE -> R.drawable.ic_outline_assignment
        CategoryName.PROPERTY_TAXES -> R.drawable.ic_round_attach_money
        CategoryName.HOME_MAINTENANCE -> R.drawable.ic_outline_maintenance
        CategoryName.HOA_FEES -> R.drawable.ic_outline_home

        // TRANSPORTATION
        CategoryName.CAR_PAYMENT -> R.drawable.ic_outline_payments
        CategoryName.GAS -> R.drawable.ic_outline_gas
        CategoryName.INSURANCE -> R.drawable.ic_outline_transportation_insurance
        CategoryName.PARKING_FEES -> R.drawable.ic_outline_local_parking
        CategoryName.PUBLIC_TRANSPORTATION -> R.drawable.ic_outline_directions_bus
        CategoryName.MAINTENANCE -> R.drawable.ic_outline_handyman

        // FOOD
        CategoryName.GROCERIES -> R.drawable.ic_outline_local_grocery_store
        CategoryName.RESTAURANTS -> R.drawable.ic_outline_restaurant_menu
        CategoryName.DINING_OUT -> R.drawable.ic_outline_fastfood

        // ENTERTAINMENT
        CategoryName.MOVIES -> R.drawable.ic_outline_movie
        CategoryName.CONCERTS -> R.drawable.ic_rounded_event
        CategoryName.SUBSCRIPTIONS -> R.drawable.ic_baseline_subscriptions
        CategoryName.HOBBIES -> R.drawable.ic_outline_emoji_events
        CategoryName.TRAVEL -> R.drawable.ic_outline_airplanemode_active

        // EDUCATION
        CategoryName.TUITION -> R.drawable.ic_outline_school
        CategoryName.BOOKS -> R.drawable.ic_outline_book
        CategoryName.SUPPLIES -> R.drawable.ic_outline_inventory_2

        // HEALTHCARE
        CategoryName.DOCTORS_VISITS -> R.drawable.ic_rounded_calendar_today
        CategoryName.PRESCRIPTIONS -> R.drawable.ic_rounded_local_pharmacy
        CategoryName.INSURANCE_PREMIUMS -> R.drawable.ic_outline_monetization_on
        CategoryName.DENTAL_CARE -> R.drawable.ic_rounded_health_and_safety
        CategoryName.VISION_CARE -> R.drawable.ic_outline_remove_red_eye

        // PERSONAL CARE
        CategoryName.HAIR -> R.drawable.ic_outline_face_retouching
        CategoryName.NAILS -> R.drawable.ic_rounded_spa
        CategoryName.CLOTHING -> R.drawable.ic_round_checkroom
        CategoryName.TOILETRIES -> R.drawable.ic_rounded_soap

        // SHOPPING
        CategoryName.GIFTS_EXPENSE -> R.drawable.ic_rounded_celebration
        CategoryName.CLOTHES -> R.drawable.ic_round_checkroom
        CategoryName.ELECTRONICS -> R.drawable.ic_rounded_devices
        CategoryName.HOUSEHOLD_ITEMS -> R.drawable.ic_rounded_weekend

        // DEBT
        CategoryName.CREDIT_CARD_PAYMENTS -> R.drawable.ic_rounded_credit_card
        CategoryName.LOAN_PAYMENTS -> R.drawable.ic_outline_payments
        CategoryName.INTEREST_EXPENSE -> R.drawable.ic_round_attach_money

        else -> R.drawable.fubuki_stare // Default case
    }
}