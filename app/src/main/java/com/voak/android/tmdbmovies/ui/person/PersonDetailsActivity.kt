package com.voak.android.tmdbmovies.ui.person

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.voak.android.tmdbmovies.BaseActivity
import com.voak.android.tmdbmovies.R

class PersonDetailsActivity : BaseActivity() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_person_details
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewRouter.showPersonDetailsFragment(intent.getIntExtra(EXTRA_PERSON_ID, -1))
    }

    companion object {
        private const val EXTRA_PERSON_ID = "person_id"

        fun newIntent(id: Int, context: Context): Intent {
            val intent = Intent(context, PersonDetailsActivity::class.java)
            intent.putExtra(EXTRA_PERSON_ID, id)

            return intent
        }
    }
}