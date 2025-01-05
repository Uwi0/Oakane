package com.kakapo.model.wallet

import com.kakapo.model.category.CategoryIconName

data class WalletItemModel(
    val id: Long = DEFAULT_ID,
    val name: String = "All Wallet",
    val isDefault: Boolean = true,
    val icon: String = "",
    val currency: String = "",
    val balance: Double = 0.0,
    val color: String = "",
    val income: Double = 0.0,
    val expense: Double = 0.0,
    val isSelected: Boolean = false
){
    val iconName: CategoryIconName
        get() {
        return if (isDefault) CategoryIconName.fromString(icon)
        else CategoryIconName.SALARY
    }

    fun isDefaultWallet() = id == DEFAULT_ID

    companion object {
        const val DEFAULT_ID = 9999L
    }
}

val defaultWalletItem = WalletItemModel()