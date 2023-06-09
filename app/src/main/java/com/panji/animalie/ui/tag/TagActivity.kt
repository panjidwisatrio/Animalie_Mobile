package com.panji.animalie.ui.tag

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.panji.animalie.R
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.databinding.ActivityTagBinding
import com.panji.animalie.model.response.TagResponse
import com.panji.animalie.ui.fragments.adapter.TagAdapter
import com.panji.animalie.util.BottomNavigationHelper
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagActivity : AppCompatActivity(), ViewStateCallback<TagResponse> {

    private lateinit var binding: ActivityTagBinding
    private lateinit var adapterTag: TagAdapter
    private val viewModel: ViewModelTag by viewModels()

    private var query: String? = null

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showAppClosingDialog()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTagBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.bottomNavigation.selectedItemId = R.id.tag

        //setup bottomNavigation
        BottomNavigationHelper.setupBottomNavigationBar(
            binding.bottomNavigation.bottomNavigation,
            this,
            this
        )

        adapterTag = TagAdapter(this@TagActivity)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        //show recyclerView
        getAllTag()
        showRecyclerView()
        searchTag()
    }

    private fun searchTag() {
        binding.searchBar.searchBarLayout.apply {
            setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    getAllTag(newText)
                    return false
                }
            })
        }
    }

    private fun showAppClosingDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Keluar dari aplikasi?")
            .setMessage("Apakah kamu yakin ingin keluar dari aplikasi?")
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Ya") { _, _ ->
                finish()
            }
            .show()
    }

    private fun showRecyclerView() {
        binding.tagRecyclerView.apply {
            adapter = adapterTag
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun getAllTag(querys: String? = null) {
        query = if (querys == "") {
            null
        } else {
            querys
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getTag(query)
                .observe(this@TagActivity) {
                    when (it) {
                        is Resource.Error -> onFailed(it.message)
                        is Resource.Loading -> onLoading()
                        is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                    }
                }
        }
    }

    override fun onSuccess(data: TagResponse) {
        adapterTag.submitList(data.tag)

        binding.apply {
            progressBar.visibility = invisible
            tagRecyclerView.visibility = visible
            createFab.createFab.visibility = visible
        }
    }

    override fun onLoading() {
        binding.apply {
            progressBar.visibility = visible
            tagRecyclerView.visibility = invisible
            createFab.createFab.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        binding.apply {
            progressBar.visibility = invisible
            tagRecyclerView.visibility = invisible
            createFab.createFab.visibility = invisible

            errorText.visibility = visible
            errorText.text = message
        }
    }
}