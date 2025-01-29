package com.kakapo.data.repository.impl

import com.kakapo.data.model.toWalletEntity
import com.kakapo.data.model.toWalletItemModel
import com.kakapo.data.model.toWalletModel
import com.kakapo.data.repository.base.WalletRepository
import com.kakapo.database.datasource.base.WalletLocalDatasource
import com.kakapo.model.asCurrency
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.model.wallet.WalletModel
import com.kakapo.preference.constant.LongKey
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.utils.getSavedCurrency
import com.kakapo.preference.datasource.utils.getWalletId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class WalletRepositoryImpl(
    private val localDatasource: WalletLocalDatasource,
    private val preferenceDatasource: PreferenceDatasource,
    private val dispatcher: CoroutineDispatcher
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

    override suspend fun update(balance: Double, id: Long): Result<Unit> {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        return localDatasource.update(balance, currentTime, id)
    }

    override suspend fun loadWalletBy(id: Long): Result<WalletModel> {
        val currency = preferenceDatasource.getSavedCurrency().asCurrency()
        return localDatasource.getWalletBy(id).mapCatching { walletEntity ->
            walletEntity.toWalletModel(currency)
        }
    }

    override suspend fun loadWalletItemBy(id: Long): Result<WalletItemModel> {
        val currency = preferenceDatasource.getSavedCurrency().asCurrency()
        return localDatasource.getWalletBy(id).mapCatching { walletEntity ->
            walletEntity.toWalletItemModel(id, currency)
        }
    }

    override suspend fun save(wallet: WalletModel): Result<Unit> {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val walletEntity = wallet.toWalletEntity(currentTime)
        return localDatasource.insert(walletEntity)
    }

    override fun loadWalletItems(): Flow<Result<List<WalletItemModel>>> = combine(
        flow { emit(preferenceDatasource.getWalletId()) },
        flow { emit(preferenceDatasource.getSavedCurrency().asCurrency()) }
    ) { walletId, currency -> walletId to currency }.flatMapLatest { (walletId, currency) ->
        localDatasource.getWallets().map { result ->
            result.mapCatching { wallets ->
                wallets.map{ it.toWalletItemModel(walletId, currency) }
            }
        }
    }.flowOn(dispatcher)

    override fun loadWallets(): Flow<Result<List<WalletModel>>> = flow {
        emit(preferenceDatasource.getSavedCurrency().asCurrency())
    }.flatMapLatest { currency ->
        localDatasource.getWallets().map { result ->
            result.mapCatching { wallets -> wallets.map { it.toWalletModel(currency) } }
        }
    }.flowOn(dispatcher)

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