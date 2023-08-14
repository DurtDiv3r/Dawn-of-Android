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

    // Names
    const val SIGN_IN_REQUEST = "signInRequest"
    const val SIGN_UP_REQUEST = "signUpRequest"

    const val STAR_FLOAT = 1.7f
    const val STAR_PATH = "M14,21.288,21.416,26l-1.968-8.88L26,11.145l-8.628-.771L14,2l-3.372,8.375L2,11.145,8.552,17.12,6.584,26Z"
}
