package com.panji.animalie.util

import com.panji.animalie.R.string.tab_discussion
import com.panji.animalie.R.string.tab_home
import com.panji.animalie.R.string.tab_interest
import com.panji.animalie.R.string.tab_mypost
import com.panji.animalie.R.string.tab_savedpost
import com.panji.animalie.R.string.tab_unanswerd
object Constanta {
    const val BASE_URL = "http://127.0.0.1:8000/api/"
    const val URL_IMAGE = "http://127.0.0.1:8000/storage/"

    const val EXTRA_POST = "extra_post"
    const val EXTRA_USER = "extra_user"

    val TAB_TITLES = intArrayOf(
        tab_home,
        tab_interest,
        tab_unanswerd
    )

    val TAB_TITLES_PROFILE = intArrayOf(
        tab_mypost,
        tab_discussion,
        tab_savedpost
    )

    val TAB_TITLES_OTHER_PROFILE = intArrayOf(
        tab_mypost,
        tab_discussion,
    )
}