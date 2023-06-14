package com.panji.animalie.util

import com.panji.animalie.R.string.tab_discussion
import com.panji.animalie.R.string.tab_home
import com.panji.animalie.R.string.tab_interest
import com.panji.animalie.R.string.tab_mypost
import com.panji.animalie.R.string.tab_other_post
import com.panji.animalie.R.string.tab_savedpost
import com.panji.animalie.R.string.tab_unanswerd
object Constanta {
    const val BASE_URL = "https://animalie.my.id/api/"
    const val URL_WEB = "https://animalie.my.id/"
    const val URL_IMAGE = "https://animalie.my.id/storage/"

    const val EXTRA_POST = "extra_post"
    const val EXTRA_TITLE = "extra_title"
    const val EXTRA_CATEGORY = "extra_category"
    const val EXTRA_CONTENT = "extra_content"
    const val EXTRA_TAG = "extra_tag"
    const val EXTRA_SLUG = "extra_slug"
    const val EXTRA_USER = "extra_user"

    const val READ_STORAGE_PERMISSION_REQUEST_CODE = 1

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
        tab_other_post,
        tab_discussion,
    )
}