package me.manulorenzo.moneytransactions.data.model.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDateTime
import java.math.BigDecimal

@Parcelize
data class Transaction(
    var amount: BigDecimal,
    var balanceBefore: BigDecimal,
    var balanceAfter: BigDecimal,
    val description: String,
    val otherAccount: String,
    val date: LocalDateTime
) : Parcelable