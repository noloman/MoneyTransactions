package me.manulorenzo.moneyyoutransaction.data.model.ui

import org.threeten.bp.LocalDateTime
import java.math.BigDecimal

data class TransactionEntity(
    var amount: BigDecimal,
    var balanceBefore: BigDecimal,
    var balanceAfter: BigDecimal,
    val description: String,
    val otherAccount: String,
    val date: LocalDateTime
)