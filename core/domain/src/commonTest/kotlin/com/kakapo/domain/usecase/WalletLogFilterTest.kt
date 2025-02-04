package com.kakapo.domain.usecase
import com.kakapo.model.Currency
import com.kakapo.model.goal.GoalSavingModel
import com.kakapo.model.transaction.TransactionModel
import com.kakapo.model.wallet.FilterWalletLogByCategory
import com.kakapo.model.wallet.FilterWalletLogByDateModel
import com.kakapo.model.wallet.WalletLogItem
import com.kakapo.model.wallet.WalletTransferModel
import com.kakapo.model.wallet.WalletTransferType
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WalletLogFilterTest {
    private lateinit var testData: List<WalletLogItem<*>>
    private lateinit var mockClock: TestClock

    @BeforeTest
    fun setup() {
        mockClock = TestClock()

        val now = mockClock.now().toEpochMilliseconds()
        val threeDaysAgo = now - (3 * 24 * 60 * 60 * 1000)
        val oneWeekAgo = now - (7 * 24 * 60 * 60 * 1000)
        val threeWeeksAgo = now - (21 * 24 * 60 * 60 * 1000)

        testData = listOf(
            WalletLogItem.TransactionLogItem(
                TransactionModel(
                    id = 1,
                    title = "Grocery Shopping",
                    dateCreated = threeDaysAgo,
                    amount = 50.0
                )
            ),
            WalletLogItem.GoalSavingLogItem(
                GoalSavingModel(
                    id = 2,
                    amount = 1000.0,
                    note = "Vacation Fund",
                    createdAt = oneWeekAgo
                )
            ),
            WalletLogItem.WalletTransferLogItem(
                WalletTransferModel(
                    id = 3,
                    name = "Transfer to Savings",
                    amount = 500.0,
                    notes = "Monthly savings",
                    createdAt = threeWeeksAgo,
                    type = WalletTransferType.Outgoing,
                    currency = Currency.IDR
                )
            )
        )
    }

    @Test
    fun `test filter by query`() {
        val result = testData.filterWalletLogsBy(
            query = "grocery",
            date = FilterWalletLogByDateModel.All,
            category = FilterWalletLogByCategory.All,
            clock = mockClock
        )

        assertEquals(1, result.size)
        assertTrue(result[0] is WalletLogItem.TransactionLogItem)
        assertEquals("Grocery Shopping", result[0].name)
    }

    @Test
    fun `test filter by past week`() {
        val result = testData.filterWalletLogsBy(
            query = "",
            date = FilterWalletLogByDateModel.PastWeek,
            category = FilterWalletLogByCategory.All,
            clock = mockClock
        )

        assertEquals(2, result.size)
        assertTrue(result[0] is WalletLogItem.TransactionLogItem, "result $result")
    }

    @Test
    fun `test filter by past month`() {
        val result = testData.filterWalletLogsBy(
            query = "",
            date = FilterWalletLogByDateModel.PastMonth,
            category = FilterWalletLogByCategory.All,
            clock = mockClock
        )

        assertEquals(3, result.size, "Expected 2 items, but got $result")
    }

    @Test
    fun `test filter by custom date range`() {
        val now = mockClock.now().toEpochMilliseconds()
        val oneWeekAgo = now - (7 * 24 * 60 * 60 * 1000)

        val result = testData.filterWalletLogsBy(
            query = "",
            date = FilterWalletLogByDateModel.Custom(oneWeekAgo, now),
            category = FilterWalletLogByCategory.All,
            clock = mockClock
        )

        assertEquals(2, result.size, "result $result")
    }

    @Test
    fun `test filter by category - transactions`() {
        val result = testData.filterWalletLogsBy(
            query = "",
            date = FilterWalletLogByDateModel.All,
            category = FilterWalletLogByCategory.Transaction,
            clock = mockClock
        )

        assertEquals(1, result.size)
        assertTrue(result[0] is WalletLogItem.TransactionLogItem)
    }

    @Test
    fun `test filter by category - goal savings`() {
        val result = testData.filterWalletLogsBy(
            query = "",
            date = FilterWalletLogByDateModel.All,
            category = FilterWalletLogByCategory.GoalSavings,
            clock = mockClock
        )

        assertEquals(1, result.size)
        assertTrue(result[0] is WalletLogItem.GoalSavingLogItem)
    }

    @Test
    fun `test filter by category - transfers`() {
        val result = testData.filterWalletLogsBy(
            query = "",
            date = FilterWalletLogByDateModel.All,
            category = FilterWalletLogByCategory.Transfer,
            clock = mockClock
        )

        assertEquals(1, result.size)
        assertTrue(result[0] is WalletLogItem.WalletTransferLogItem)
    }

    @Test
    fun `test multiple filters combined`() {
        val result = testData.filterWalletLogsBy(
            query = "savings",
            date = FilterWalletLogByDateModel.PastMonth,
            category = FilterWalletLogByCategory.Transfer,
            clock = mockClock
        )

        assertEquals(1, result.size)
        assertTrue(result[0] is WalletLogItem.WalletTransferLogItem)
    }
}

class TestClock : Clock {

    private val fixedInstant = Instant.fromEpochMilliseconds(1738668135507)

    override fun now(): Instant = fixedInstant
}