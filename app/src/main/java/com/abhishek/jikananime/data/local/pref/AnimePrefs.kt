package com.abhishek.jikananime.data.local.pref

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

// Store the current page during refresh to prevent re-fetching pages
// already in the database and to handle page limit constraints
class AnimePrefs @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_CURRENT_PAGE = "current_page"
        private const val KEY_TOTAL_PAGES = "total_pages"
    }

    var currentPage: Int
        get() = sharedPreferences.getInt(KEY_CURRENT_PAGE, 0)
        set(value) = sharedPreferences.edit { putInt(KEY_CURRENT_PAGE, value) }

    var totalPages: Int
        get() = sharedPreferences.getInt(KEY_TOTAL_PAGES, Int.MAX_VALUE)
        set(value) = sharedPreferences.edit { putInt(KEY_TOTAL_PAGES, value) }
}