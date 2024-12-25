package com.kakapo.oakane.domain.usecase.impl

import com.kakapo.oakane.data.repository.base.MonthlyBudgetRepository
import com.kakapo.oakane.data.repository.base.TransactionRepository
import com.kakapo.oakane.domain.usecase.base.GetMonthlyBudgetOverviewUseCase
import com.kakapo.oakane.model.monthlyBudget.MonthlyBudgetOverViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetMonthlyBudgetOverviewUseCaseImpl(
    private val monthlyBudgetRepository: MonthlyBudgetRepository,
    private val transactionRepository: TransactionRepository
) : GetMonthlyBudgetOverviewUseCase {

    override suspend fun execute(
        walletId: Long?,
        startDateOfMont: Long,
        endDateOfMonth: Long
    ): Result<MonthlyBudgetOverViewModel> = coroutineScope {
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
            val limitDeferred = async { monthlyBudgetRepository.loadLimit() }

            val totalIncome = totalIncomeDeferred.await().getOrNull() ?: 0.0
            val totalExpense = totalExpenseDeferred.await().getOrNull() ?: 0.0
            val limit = limitDeferred.await().getOrNull() ?: 0.0
            val progress = if (limit == 0.0) 0.0 else totalExpense / limit

            MonthlyBudgetOverViewModel(
                totalIncome = totalIncome,
                totalExpense = totalExpense,
                spent = totalExpense,
                left = limit - totalExpense,
                limit = limit,
                progress = progress.toFloat()
            )
        }
    }
}