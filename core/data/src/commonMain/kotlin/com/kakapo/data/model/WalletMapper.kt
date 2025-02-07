package com.kakapo.data.model

import com.kakapo.common.asLong
import com.kakapo.database.model.WalletEntity
import com.kakapo.model.Currency
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.model.wallet.WalletModel

fun WalletEntity.toWalletModel(currency: Currency): WalletModel {
    return WalletModel(
        id = id,
        currency = currency,
        balance = balance,
        name = name,
        color = color,
        isDefaultIcon = isDefaultIcon == 1L,
        icon = icon,
        note = note
    )
}

fun WalletEntity.toWalletItemModel(walletId: Long, currency: Currency): WalletItemModel {
    return WalletItemModel(
        id = id,
        name = name,
        isDefault = isDefaultIcon == 1L,
        icon = icon,
        color = color,
        currency = currency,
        balance = balance,
        income = income,
        expense = expense,
        isSelected = walletId == id,
        note = note
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
        note = note,
        createdAt = time,
        updateAt = time
    )
}