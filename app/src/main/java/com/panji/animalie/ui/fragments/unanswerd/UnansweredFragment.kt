package com.panji.animalie.ui.fragments.unanswerd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.panji.animalie.R
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.databinding.FragmentUnansweredBinding
import com.panji.animalie.model.response.PostResponse
import com.panji.animalie.ui.detail.ViewModelDetailPost
import com.panji.animalie.ui.fragments.adapter.PostAdapter
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UnansweredFragment : Fragment(), ViewStateCallback<PostResponse> {

    private lateinit var binding: FragmentUnansweredBinding
    private val viewModel: ViewModelUnanswerd by viewModels()
    private lateinit var adapterUnanswerd: PostAdapter
    private var typePost: String = ""
    private var chipInterest: String? = null
    private var selectedTag: String? = null
    private var query: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typePost = it.getString(KEY_BUNDLE).toString()
            chipInterest = it.getString(CHIP_INTEREST)
            selectedTag = it.getString(SELECTED_TAG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUnansweredBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapterUnanswerd = PostAdapter(
            context,
            viewModel = ViewModelDetailPost(application = requireActivity().application),
            lifecycleOwner = viewLifecycleOwner
        )

        getUnansweredPost()
        setSwipeRefresh()
        showRecycleView()
    }

    private fun setSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            getUnansweredPost()
        }
    }

    fun getUnansweredPost(querys: String? = null) {
        query = if (querys == "") {
            null
        } else {
            querys
        }
        // get data from viewmodel
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getUnanswerdPost(typePost, chipInterest, selectedTag, query).observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                    is Resource.Error -> onFailed(it.message)
                }
            }
        }
    }

    private fun showRecycleView() {
        // inisialisasi adapter
        binding.unanswerdRecyclerView.apply {
            adapter = adapterUnanswerd
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onSuccess(data: PostResponse) {
        adapterUnanswerd.submitList(emptyList())
        // set data ke adapter
        binding.apply {
            if (data.posts.data.isEmpty()) {
                errorText.visibility = visible
                progressBar.visibility = invisible
                errorText.text = getString(R.string.empty_data)
            } else {
                adapterUnanswerd.submitList(data.posts.data)
                unanswerdRecyclerView.visibility = visible
                progressBar.visibility = invisible
                errorText.visibility = invisible
            }
        }
    }

    override fun onLoading() {
        // show loading
        binding.apply {
            unanswerdRecyclerView.visibility = invisible
            progressBar.visibility = visible
            errorText.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        // show error
        binding.apply {
            unanswerdRecyclerView.visibility = invisible
            progressBar.visibility = invisible
            errorText.visibility = visible
            errorText.text = message
        }
    }

    companion object {
        private const val KEY_BUNDLE = "type_post"
        private const val CHIP_INTEREST = "chip_interest"
        private const val SELECTED_TAG = "selected_tag"
        fun getInstance(
            typePost: String,
            chipInterest: String? = null,
            selectedTag: String? = null
        ): Fragment {
            return UnansweredFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, typePost)
                    putString(CHIP_INTEREST, chipInterest)
                    putString(SELECTED_TAG, selectedTag)
                }
            }
        }
    }
}