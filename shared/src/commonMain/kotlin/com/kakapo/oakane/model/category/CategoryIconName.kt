package com.kakapo.oakane.model.category

enum class CategoryIconName(val displayName: String, val parentCategory: ParentCategory) {

    // INCOME
    SALARY("Salary", ParentCategory.INCOME),
    FREELANCE("Freelance", ParentCategory.INCOME),
    BUSINESS_PROFITS("Business Profits", ParentCategory.INCOME),
    RENTAL_INCOME("Rental Income", ParentCategory.INCOME),
    INTEREST("Interest", ParentCategory.INCOME),
    DIVIDENDS("Dividends", ParentCategory.INCOME),
    GIFTS("Gifts", ParentCategory.INCOME),

    // HOUSING
    RENT_MORTGAGE("Rent/Mortgage", ParentCategory.HOUSING),
    UTILITIES("Utilities", ParentCategory.HOUSING),
    HOME_INSURANCE("Home Insurance", ParentCategory.HOUSING),
    PROPERTY_TAXES("Property Taxes", ParentCategory.HOUSING),
    HOME_MAINTENANCE("Home Maintenance", ParentCategory.HOUSING),
    HOA_FEES("HOA Fees", ParentCategory.HOUSING),

    // TRANSPORTATION
    CAR_PAYMENT("Car Payment", ParentCategory.TRANSPORTATION),
    GAS("Gas", ParentCategory.TRANSPORTATION),
    INSURANCE("Insurance", ParentCategory.TRANSPORTATION),
    PARKING_FEES("Parking Fees", ParentCategory.TRANSPORTATION),
    PUBLIC_TRANSPORTATION("Public Transportation", ParentCategory.TRANSPORTATION),
    MAINTENANCE("Maintenance", ParentCategory.TRANSPORTATION),

    // FOOD
    GROCERIES("Groceries", ParentCategory.FOOD),
    RESTAURANTS("Restaurants", ParentCategory.FOOD),
    DINING_OUT("Dining Out", ParentCategory.FOOD),

    // ENTERTAINMENT
    MOVIES("Movies", ParentCategory.ENTERTAINMENT),
    CONCERTS("Concerts", ParentCategory.ENTERTAINMENT),
    SUBSCRIPTIONS("Subscriptions", ParentCategory.ENTERTAINMENT),
    HOBBIES("Hobbies", ParentCategory.ENTERTAINMENT),
    TRAVEL("Travel", ParentCategory.ENTERTAINMENT),

    // EDUCATION
    TUITION("Tuition", ParentCategory.EDUCATION),
    BOOKS("Books", ParentCategory.EDUCATION),
    SUPPLIES("Supplies", ParentCategory.EDUCATION),

    // HEALTHCARE
    DOCTORS_VISITS("Doctors Visits", ParentCategory.HEALTHCARE),
    PRESCRIPTIONS("Prescriptions", ParentCategory.HEALTHCARE),
    INSURANCE_PREMIUMS("Insurance Premiums", ParentCategory.HEALTHCARE),
    DENTAL_CARE("Dental Care", ParentCategory.HEALTHCARE),
    VISION_CARE("Vision Care", ParentCategory.HEALTHCARE),

    // PERSONAL CARE
    HAIR("Hair", ParentCategory.PERSONAL_CARE),
    NAILS("Nails", ParentCategory.PERSONAL_CARE),
    CLOTHING("Clothing", ParentCategory.PERSONAL_CARE),
    TOILETRIES("Toiletries", ParentCategory.PERSONAL_CARE),

    // SHOPPING
    GIFTS_EXPENSE("Gifts Expense", ParentCategory.SHOPPING),
    CLOTHES("Clothes", ParentCategory.SHOPPING),
    ELECTRONICS("Electronics", ParentCategory.SHOPPING),
    HOUSEHOLD_ITEMS("Household Items", ParentCategory.SHOPPING),

    // DEBT
    CREDIT_CARD_PAYMENTS("Credit Card Payments", ParentCategory.DEBT),
    LOAN_PAYMENTS("Loan Payments", ParentCategory.DEBT),
    INTEREST_EXPENSE("Interest Expense", ParentCategory.DEBT),

    //Default
    DEFAULT("Default", ParentCategory.INCOME);

    companion object {
        // Map a string to the corresponding enum value
        fun fromString(value: String): CategoryIconName {
            return entries.find { it.displayName == value } ?: SALARY
        }
    }
}

enum class ParentCategory(val displayName: String) {
    INCOME("Income"),
    HOUSING("Housing"),
    TRANSPORTATION("Transportation"),
    FOOD("Food"),
    ENTERTAINMENT("Entertainment"),
    EDUCATION("Education"),
    HEALTHCARE("Healthcare"),
    PERSONAL_CARE("Personal Care"),
    SHOPPING("Shopping"),
    DEBT("Debt");
}


val categoryMap: Map<ParentCategory, List<CategoryIconName>> = mapOf(
    ParentCategory.INCOME to listOf(
        CategoryIconName.SALARY,
        CategoryIconName.FREELANCE,
        CategoryIconName.BUSINESS_PROFITS,
        CategoryIconName.RENTAL_INCOME,
        CategoryIconName.INTEREST,
        CategoryIconName.DIVIDENDS,
        CategoryIconName.GIFTS
    ),
    ParentCategory.HOUSING to listOf(
        CategoryIconName.RENT_MORTGAGE,
        CategoryIconName.UTILITIES,
        CategoryIconName.HOME_INSURANCE,
        CategoryIconName.PROPERTY_TAXES,
        CategoryIconName.HOME_MAINTENANCE,
        CategoryIconName.HOA_FEES
    ),
    ParentCategory.TRANSPORTATION to listOf(
        CategoryIconName.CAR_PAYMENT,
        CategoryIconName.GAS,
        CategoryIconName.INSURANCE,
        CategoryIconName.PARKING_FEES,
        CategoryIconName.PUBLIC_TRANSPORTATION,
        CategoryIconName.MAINTENANCE
    ),
    ParentCategory.FOOD to listOf(
        CategoryIconName.GROCERIES,
        CategoryIconName.RESTAURANTS,
        CategoryIconName.DINING_OUT
    ),
    ParentCategory.ENTERTAINMENT to listOf(
        CategoryIconName.MOVIES,
        CategoryIconName.CONCERTS,
        CategoryIconName.SUBSCRIPTIONS,
        CategoryIconName.HOBBIES,
        CategoryIconName.TRAVEL
    ),
    ParentCategory.EDUCATION to listOf(
        CategoryIconName.TUITION,
        CategoryIconName.BOOKS,
        CategoryIconName.SUPPLIES
    ),
    ParentCategory.HEALTHCARE to listOf(
        CategoryIconName.DOCTORS_VISITS,
        CategoryIconName.PRESCRIPTIONS,
        CategoryIconName.INSURANCE_PREMIUMS,
        CategoryIconName.DENTAL_CARE,
        CategoryIconName.VISION_CARE
    ),
    ParentCategory.PERSONAL_CARE to listOf(
        CategoryIconName.HAIR,
        CategoryIconName.NAILS,
        CategoryIconName.CLOTHING,
        CategoryIconName.TOILETRIES
    ),
    ParentCategory.SHOPPING to listOf(
        CategoryIconName.GIFTS_EXPENSE,
        CategoryIconName.CLOTHES,
        CategoryIconName.ELECTRONICS,
        CategoryIconName.HOUSEHOLD_ITEMS
    ),
    ParentCategory.DEBT to listOf(
        CategoryIconName.CREDIT_CARD_PAYMENTS,
        CategoryIconName.LOAN_PAYMENTS,
        CategoryIconName.INTEREST_EXPENSE
    )
)

