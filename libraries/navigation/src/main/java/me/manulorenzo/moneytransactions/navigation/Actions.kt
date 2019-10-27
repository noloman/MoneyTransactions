package me.manulorenzo.moneytransactions.navigation

import android.content.Context
import android.content.Intent
import android.os.Parcelable

object Actions {
    fun openTransactionsIntent(
        context: Context,
        extra: Parcelable
    ) = internalIntent(context, "me.manulorenzo.moneytransactions.transaction.open", extra)

    // TODO Come up with a better name for the extra
    private fun internalIntent(context: Context, action: String, extra: Parcelable) =
        Intent(action).setPackage(context.packageName).putExtra("extra", extra)
}