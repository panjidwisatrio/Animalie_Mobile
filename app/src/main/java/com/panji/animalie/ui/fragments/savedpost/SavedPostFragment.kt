package com.panji.animalie.ui.fragments.savedpost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.panji.animalie.R
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.databinding.FragmentSavedPostBinding
import com.panji.animalie.model.response.PostResponse
import com.panji.animalie.ui.detail.ViewModelDetailPost
import com.panji.animalie.ui.fragments.adapter.PostAdapter
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedPostFragment : Fragment(), ViewStateCallback<PostResponse> {

    private lateinit var binding: FragmentSavedPostBinding
    private lateinit var adapterSavedPost: PostAdapter
    private val viewModel: ViewModelSavedPost by viewModels()
    private var token: String = ""
    private var currentPage: Int? = 1
    private var totalPage: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            token = it.getString(KEY_TOKEN).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding  = FragmentSavedPostBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterSavedPost = PostAdapter(
            context,
            type = "my_post",
            viewModel = ViewModelDetailPost(application = requireActivity().application),
            lifecycleOwner = viewLifecycleOwner
        )

        getBookmark()
        showRecycleView()
    }

    private fun getBookmark() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getSavedPost(token, currentPage)
                .observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Error -> onFailed(it.message)
                        is Resource.Loading -> onLoading()
                        is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                    }
                }
        }
    }

    private fun showRecycleView() {
        binding.recyclerView.apply {
            adapter = adapterSavedPost
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onSuccess(data: PostResponse) {
        if (data.posts.data.isEmpty()) {
            binding.apply {
                recyclerView.visibility = invisible
                progressBar.visibility = invisible
                errorText.visibility = visible
                errorText.text = getString(R.string.empty_data)
            }
            return
        }

        totalPage = data.posts.lastPage
        adapterSavedPost.submitList(data.posts.data)

        binding.apply {
            recyclerView.visibility = visible
            progressBar.visibility = invisible
            errorText.visibility = invisible
        }
    }

    override fun onLoading() {
        binding.apply {
            recyclerView.visibility = invisible
            progressBar.visibility = visible
            errorText.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        binding.apply {
            recyclerView.visibility = invisible
            progressBar.visibility = invisible
            errorText.visibility = visible
            errorText.text = message
        }
    }

    companion object {
        private const val KEY_TOKEN = "token"
        fun getInstance(token: String): Fragment {
            return SavedPostFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_TOKEN, token)
                }
            }
        }
    }
}