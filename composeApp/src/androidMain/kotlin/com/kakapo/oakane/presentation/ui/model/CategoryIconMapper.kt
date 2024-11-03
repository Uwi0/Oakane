package com.kakapo.oakane.presentation.ui.model

import com.kakapo.oakane.R
import com.kakapo.oakane.model.category.CategoryIconName

fun CategoryIconName.asIcon(): Int {
    return when (this) {
        // INCOME
        CategoryIconName.SALARY -> R.drawable.ic_rounded_work
        CategoryIconName.FREELANCE -> R.drawable.ic_rounded_home_work
        CategoryIconName.BUSINESS_PROFITS -> R.drawable.ic_rounded_trending_up
        CategoryIconName.RENTAL_INCOME -> R.drawable.ic_outline_insights
        CategoryIconName.INTEREST -> R.drawable.ic_round_attach_money
        CategoryIconName.DIVIDENDS -> R.drawable.ic_rounded_show_chart
        CategoryIconName.GIFTS -> R.drawable.ic_outline_card_giftcard

        // HOUSING
        CategoryIconName.RENT_MORTGAGE -> R.drawable.ic_rounded_real_estate_agent
        CategoryIconName.UTILITIES -> R.drawable.ic_outline_lightbulb
        CategoryIconName.HOME_INSURANCE -> R.drawable.ic_outline_assignment
        CategoryIconName.PROPERTY_TAXES -> R.drawable.ic_round_attach_money
        CategoryIconName.HOME_MAINTENANCE -> R.drawable.ic_outline_maintenance
        CategoryIconName.HOA_FEES -> R.drawable.ic_outline_home

        // TRANSPORTATION
        CategoryIconName.CAR_PAYMENT -> R.drawable.ic_outline_payments
        CategoryIconName.GAS -> R.drawable.ic_outline_gas
        CategoryIconName.INSURANCE -> R.drawable.ic_outline_transportation_insurance
        CategoryIconName.PARKING_FEES -> R.drawable.ic_outline_local_parking
        CategoryIconName.PUBLIC_TRANSPORTATION -> R.drawable.ic_outline_directions_bus
        CategoryIconName.MAINTENANCE -> R.drawable.ic_outline_handyman

        // FOOD
        CategoryIconName.GROCERIES -> R.drawable.ic_outline_local_grocery_store
        CategoryIconName.RESTAURANTS -> R.drawable.ic_outline_restaurant_menu
        CategoryIconName.DINING_OUT -> R.drawable.ic_outline_fastfood

        // ENTERTAINMENT
        CategoryIconName.MOVIES -> R.drawable.ic_outline_movie
        CategoryIconName.CONCERTS -> R.drawable.ic_rounded_event
        CategoryIconName.SUBSCRIPTIONS -> R.drawable.ic_baseline_subscriptions
        CategoryIconName.HOBBIES -> R.drawable.ic_outline_emoji_events
        CategoryIconName.TRAVEL -> R.drawable.ic_outline_airplanemode_active

        // EDUCATION
        CategoryIconName.TUITION -> R.drawable.ic_outline_school
        CategoryIconName.BOOKS -> R.drawable.ic_outline_book
        CategoryIconName.SUPPLIES -> R.drawable.ic_outline_inventory_2

        // HEALTHCARE
        CategoryIconName.DOCTORS_VISITS -> R.drawable.ic_rounded_calendar_today
        CategoryIconName.PRESCRIPTIONS -> R.drawable.ic_rounded_local_pharmacy
        CategoryIconName.INSURANCE_PREMIUMS -> R.drawable.ic_outline_monetization_on
        CategoryIconName.DENTAL_CARE -> R.drawable.ic_rounded_health_and_safety
        CategoryIconName.VISION_CARE -> R.drawable.ic_outline_remove_red_eye

        // PERSONAL CARE
        CategoryIconName.HAIR -> R.drawable.ic_outline_face_retouching
        CategoryIconName.NAILS -> R.drawable.ic_rounded_spa
        CategoryIconName.CLOTHING -> R.drawable.ic_round_checkroom
        CategoryIconName.TOILETRIES -> R.drawable.ic_rounded_soap

        // SHOPPING
        CategoryIconName.GIFTS_EXPENSE -> R.drawable.ic_rounded_celebration
        CategoryIconName.CLOTHES -> R.drawable.ic_round_checkroom
        CategoryIconName.ELECTRONICS -> R.drawable.ic_rounded_devices
        CategoryIconName.HOUSEHOLD_ITEMS -> R.drawable.ic_rounded_weekend

        // DEBT
        CategoryIconName.CREDIT_CARD_PAYMENTS -> R.drawable.ic_rounded_credit_card
        CategoryIconName.LOAN_PAYMENTS -> R.drawable.ic_outline_payments
        CategoryIconName.INTEREST_EXPENSE -> R.drawable.ic_round_attach_money

        //Default
        CategoryIconName.DEFAULT -> R.drawable.ic_outline_account_balance_wallet
    }
}