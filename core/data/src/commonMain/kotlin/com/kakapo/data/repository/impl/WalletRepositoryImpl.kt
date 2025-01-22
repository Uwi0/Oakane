package com.kakapo.data.repository.impl

import com.kakapo.data.model.toWalletEntity
import com.kakapo.data.model.toWalletItemModel
import com.kakapo.data.model.toWalletModel
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.database.datasource.base.WalletLocalDatasource
import com.kakapo.database.model.WalletEntity
import com.kakapo.model.asCurrency
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.model.wallet.WalletModel
import com.kakapo.preference.constant.LongKey
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.utils.getSavedCurrency
import com.kakapo.preference.datasource.utils.getWalletId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock

class WalletRepositoryImpl(
    private val localDatasource: WalletLocalDatasource,
    private val preferenceDatasource: PreferenceDatasource
) : WalletRepository {

    override suspend fun createDefaultWallet(): Result<Unit> {
        return localDatasource.createDefaultWallet()
    }

    override suspend fun saveWallet(id: Long): Result<Unit> {
        return runCatching {
            preferenceDatasource.saveLongValue(LongKey.WALLET_ID, id)
        }
    }

    override suspend fun loadSelectedWallet(): Result<WalletModel> {
        val currency = preferenceDatasource.getSavedCurrency().asCurrency()
        val walletId = preferenceDatasource.getWalletId()
        return localDatasource.getWalletBy(walletId).mapCatching { it.toWalletModel(currency) }
    }

    override suspend fun update(balance: Double): Result<Unit> {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val walletId = preferenceDatasource.getWalletId()
        return localDatasource.update(balance, currentTime, walletId)
    }

    override suspend fun update(balance: Double, id: Long): Result<Unit> {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        return localDatasource.update(balance, currentTime, id)
    }

    override suspend fun loadWalletById(): Result<WalletModel> {
        val id = preferenceDatasource.getWalletId()
        val currency = preferenceDatasource.getSavedCurrency().asCurrency()
        return localDatasource.getWalletBy(id).mapCatching { walletEntity ->
            walletEntity.toWalletModel(currency)
        }
    }

    override suspend fun save(wallet: WalletModel): Result<Unit> {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val walletEntity = wallet.toWalletEntity(currentTime)
        return localDatasource.insert(walletEntity)
    }

    override fun loadWalletItems(): Flow<Result<List<WalletItemModel>>> = flow {
        val walletId = preferenceDatasource.getWalletId()
        val currency = preferenceDatasource.getSavedCurrency().asCurrency()
        val toWalletItem: (WalletEntity) -> WalletItemModel = { it.toWalletItemModel(walletId, currency) }
        val result = localDatasource.getWallets().mapCatching { it.map(toWalletItem) }
        emit(result)
    }

    override fun loadWallets(): Flow<Result<List<WalletModel>>> = flow {
        val currency = preferenceDatasource.getSavedCurrency().asCurrency()
        val toWalletModel: (WalletEntity) -> WalletModel = { it.toWalletModel(currency) }
        val result = localDatasource.getWallets().mapCatching { it.map(toWalletModel) }
        emit(result)
    }

    override suspend fun update(wallet: WalletModel): Result<Unit> {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        return localDatasource.update(wallet.toWalletEntity(currentTime))
    }

    override suspend fun deleteWalletBy(id: Long): Result<Unit> {
        return localDatasource.deleteWalletBy(id)
    }

    override suspend fun loadTotalBalance(): Result<Double> {
        return localDatasource.geTotalBalance()
    }

}