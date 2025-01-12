package com.kakapo.domain.usecase.impl

import com.kakapo.data.repository.base.MonthlyBudgetRepository
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.data.repository.base.TransactionRepository
import com.kakapo.domain.usecase.base.GetMonthlyBudgetOverviewUseCase
import com.kakapo.model.Currency
import com.kakapo.model.monthlyBudget.MonthlyBudgetOverView
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetMonthlyBudgetOverviewUseCaseImpl(
    private val monthlyBudgetRepository: MonthlyBudgetRepository,
    private val transactionRepository: TransactionRepository,
    private val systemRepository: SystemRepository
) : GetMonthlyBudgetOverviewUseCase {

    override suspend fun execute(
        walletId: Long?,
        startDateOfMont: Long,
        endDateOfMonth: Long
    ): Result<MonthlyBudgetOverView> = coroutineScope {
        runCatching {
            val totalIncomeDeferred = async {
                transactionRepository.loadTotalIncome(
                    walletId,
                    startDateOfMont,
                    endDateOfMonth
                )
            }
            val totalExpenseDeferred = async {
                transactionRepository.loadTotalExpense(
                    walletId,
                    startDateOfMont,
                    endDateOfMonth
                )
            }
            val limitDeferred = async { monthlyBudgetRepository.loadLimit(startDateOfMont, endDateOfMonth) }
            val currencyDeferred = async { systemRepository.loadSavedCurrency() }

            val totalIncome = totalIncomeDeferred.await().getOrNull() ?: 0.0
            val totalExpense = totalExpenseDeferred.await().getOrNull() ?: 0.0
            val limit = limitDeferred.await().getOrNull() ?: 0.0
            val progress = if (limit == 0.0) 0.0 else totalExpense / limit
            val currency = currencyDeferred.await().getOrNull() ?: Currency.IDR

            MonthlyBudgetOverView(
                totalIncome = totalIncome,
                totalExpense = totalExpense,
                spent = totalExpense,
                left = limit - totalExpense,
                limit = limit,
                progress = if(progress >= 1f) 1f else progress.toFloat(),
                currency = currency
            )
        }
    }
}