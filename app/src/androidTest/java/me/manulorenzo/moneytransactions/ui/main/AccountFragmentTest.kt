package me.manulorenzo.moneytransactions.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import me.manulorenzo.moneytransactions.EspressoMatchers
import me.manulorenzo.moneytransactions.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class AccountFragmentTest {
    @get:Rule
    val activityRule = ActivityTestRule(AccountActivity::class.java)

    @Test
    fun transactionsRecyclerView_shouldBeShown() {
        onView(ViewMatchers.withId(R.id.transactionsRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun transactionsRecyclerView_shouldHaveFiveItems() {
        onView(ViewMatchers.withId(R.id.transactionsRecyclerView)).check(
            matches(
                EspressoMatchers.hasItemCount(5)
            )
        )
    }
}