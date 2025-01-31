package com.kakapo.model.wallet

import com.kakapo.model.goal.GoalSavingModel
import com.kakapo.model.transaction.TransactionModel

sealed class WalletLogItem<T>(open val data: T) {
    abstract val id: Long
    abstract val uniqueId: String
    abstract val dateCreated: Long
    abstract val name: String

    data class WalletTransferLogItem(
        override val data: WalletTransferModel
    ) : WalletLogItem<WalletTransferModel>(data) {

        override val id: Long
            get() = data.id
        override val uniqueId: String
            get() = data.uniqueId
        override val dateCreated: Long
            get() = data.createdAt
        override val name: String
            get() = data.name
    }

    data class TransactionLogItem(
        override val data: TransactionModel
    ) : WalletLogItem<TransactionModel>(data) {

        override val id: Long
            get() = data.id
        override val uniqueId: String
            get() = data.uniqueId
        override val dateCreated: Long
            get() = data.dateCreated
        override val name: String
            get() = data.title
    }

    data class GoalSavingLogItem(
        override val data: GoalSavingModel
    ) : WalletLogItem<GoalSavingModel>(data) {

        override val id: Long
            get() = data.id
        override val uniqueId: String
            get() = data.uniqueId
        override val dateCreated: Long
            get() = data.createdAt
        override val name: String
            get() = ""
    }
}