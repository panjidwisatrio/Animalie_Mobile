package com.panji.animalie.ui.fragments.discussion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.panji.animalie.R
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.databinding.FragmentDiscussionBinding
import com.panji.animalie.model.response.PostResponse
import com.panji.animalie.ui.detail.ViewModelDetailPost
import com.panji.animalie.ui.adapter.PostAdapter
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiscussionFragment : Fragment(), ViewStateCallback<PostResponse> {

    private lateinit var binding: FragmentDiscussionBinding
    private lateinit var adapterDiscussion: PostAdapter
    private val viewModel: ViewModelDiscussion by viewModels()
    private var userId: String = ""
    private var currentPage: Int? = 1
    private var totalPage: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString(KEY_USER_ID).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDiscussionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterDiscussion = PostAdapter(
            context,
            type = "my_post",
            viewModel = ViewModelDetailPost(application = requireActivity().application),
            lifecycleOwner = viewLifecycleOwner
        )

        getDiscussionPost()
        showRecycleView()
    }

    private fun getDiscussionPost() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getDiscussion(userId, currentPage)
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
            adapter = adapterDiscussion
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
        adapterDiscussion.submitList(data.posts.data)

        binding.apply {
            errorText.visibility = invisible
            progressBar.visibility = invisible
            recyclerView.visibility = visible
        }
    }

    override fun onLoading() {
        binding.apply {
            errorText.visibility = invisible
            progressBar.visibility = visible
            recyclerView.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        binding.apply {
            errorText.text = message
            errorText.visibility = visible
            progressBar.visibility = invisible
            recyclerView.visibility = invisible
        }
    }

    companion object {
        private const val KEY_USER_ID = "user_id"
        fun getInstance(userId: String): Fragment {
            return DiscussionFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_USER_ID, userId)
                }
            }
        }
    }
}