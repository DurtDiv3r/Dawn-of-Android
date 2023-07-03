package com.islaharper.dawnofandroid.util

import com.islaharper.dawnofandroid.BuildConfig

object Constants {

    // BACKEND
    // Using Localhost for testing
    const val BASE_URL = "http://127.0.0.1:8080"
    const val CLIENT_ID = BuildConfig.CLIENT_ID

    // Room DB
    const val FLAVOUR_DB = "flavour_db"
    const val FLAVOUR_DB_TABLE = "flavour_table"

    // PREFS
    const val PREFS_NAME = "app_preferences"
    const val PREFS_ONBOARDING_KEY = "onboarding_complete"
    const val PREFS_SIGNED_IN_KEY = "signed_in_key"
    const val PREFS_DARK_MODE_KEY = "dark_mode"
    const val PREFS_DYNAMIC_THEME_KEY = "dynamic_theme"

    // OnBoarding
    const val ONBOARDING_PAGE_COUNT = 3
    const val ONBOARDING_LAST_PAGE = 2
}
