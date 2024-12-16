package com.kakapo.oakane.data.model

import com.kakapo.oakane.common.asLong
import com.kakapo.oakane.data.database.model.WalletEntity
import com.kakapo.oakane.model.wallet.WalletItemModel
import com.kakapo.oakane.model.wallet.WalletModel

fun WalletEntity.toWalletModel(): WalletModel {
    return WalletModel(
        id = id,
        currency = "Rp",
        balance = balance,
        name = name,
        isDefaultIcon = false,
        icon = "quas"
    )
}

fun WalletEntity.toWalletItemModel(walletId: Long): WalletItemModel{
    return WalletItemModel(
        id = id,
        name = name,
        isDefault = isDefaultIcon == 1L,
        icon = icon,
        color = color,
        currency = "IDR",
        balance = balance,
        income = income,
        expense = expense,
        isSelected = walletId == id
    )
}

fun WalletModel.toWalletEntity(time: Long): WalletEntity {
    return WalletEntity(
        id = id,
        name = name,
        balance = balance,
        color = color,
        icon = icon,
        isDefaultIcon = isDefaultIcon.asLong(),
        createdAt = time,
        updateAt = time
    )
}