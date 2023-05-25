package com.panji.animalie.ui.fragments.latest

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.panji.animalie.R
import com.panji.animalie.data.Resource
import com.panji.animalie.databinding.FragmentLatestBinding
import com.panji.animalie.model.Post
import com.panji.animalie.model.response.PostResponse
import com.panji.animalie.ui.adapter.PostAdapter
import com.panji.animalie.util.PaginationScrollListener
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LatestFragment : Fragment(), ViewStateCallback<PostResponse> {

    private lateinit var binding: FragmentLatestBinding
    private val viewModel: ViewModelLatest by viewModels()
    private lateinit var adapterLatest: PostAdapter
    private var typePost: String = ""
    private var chipInterest: String? = null
    private var isLoading = false
    private var currentPage = 1
    private var totalPage: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typePost = it.getString(KEY_BUNDLE).toString()
            chipInterest = it.getString(CHIP_INTEREST)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLatestBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapterLatest = PostAdapter(context)

        getPostLatest()
        showRecycleView()
    }

    private fun getPostLatest() {
        // get data from viewmodel
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getLatestPost(typePost, chipInterest, null, currentPage)
                .observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Error -> {
                            Log.d("LatestFragment", "typePost: $typePost")
                            Log.d("LatestFragment", "chipInterest: $chipInterest")
                            Log.d("LatestFragment", "currentPage: $currentPage")
                            onFailed(it.message)
                        }
                        is Resource.Loading -> onLoading()
                        is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                    }
                }
        }
    }

    private fun showRecycleView() {
        // inisialisasi adapter
        binding.recyclerView.apply {
            adapter = adapterLatest
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            // TODO 2: set scroll listener for pagination
//            addOnScrollListener(object : PaginationScrollListener(layoutManager as LinearLayoutManager) {
//                override fun loadMoreItems() {
//                    loadMoreItem(++currentPage)
//                }
//
//                override fun getTotalPageCount(): Int? {
//                    return totalPage
//                }
//
//                override fun isLastPage(): Boolean {
//                    return currentPage == totalPage
//                }
//
//                override fun isLoading(): Boolean {
//                    return isLoading
//                }
//            })
        }
    }

    private fun loadMoreItem(page: Int) {
        isLoading = true

        // get data from viewmodel
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getLatestPost(typePost, chipInterest, null, page)
                .observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Error -> onFailed(it.message)
                        is Resource.Loading -> binding.progressBar.visibility = visible
                        is Resource.Success -> {
                            binding.progressBar.visibility = invisible
                            adapterLatest.submitList(it.data?.posts?.data)
                        }
                    }
                }
        }

        isLoading = false
    }

    override fun onSuccess(data: PostResponse) {
        // set data ke adapter
        binding.apply {
            if (data.posts.data.isEmpty()) {
                errorText.visibility = visible
                progressBar.visibility = invisible
                errorText.text = getString(R.string.empty_data)
            } else {
                totalPage = data.posts.lastPage
                adapterLatest.submitList(data.posts.data)
                recyclerView.visibility = visible
                progressBar.visibility = invisible
                errorText.visibility = invisible
            }
        }
    }

    override fun onLoading() {
        // show loading
        binding.apply {
            recyclerView.visibility = invisible
            progressBar.visibility = visible
            errorText.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        // show error
        binding.apply {
            recyclerView.visibility = invisible
            progressBar.visibility = invisible
            errorText.visibility = visible
            errorText.text = message
        }
    }

    companion object {
        private const val KEY_BUNDLE = "type_post"
        private const val CHIP_INTEREST = "chip_interest"
        fun getInstance(typePost: String, chipInterest: String? = null): Fragment {
            return LatestFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, typePost)
                    putString(CHIP_INTEREST, chipInterest)
                }
            }
        }
    }
}