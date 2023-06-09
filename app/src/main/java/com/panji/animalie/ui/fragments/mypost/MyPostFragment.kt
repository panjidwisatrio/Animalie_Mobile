package com.panji.animalie.ui.fragments.mypost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.panji.animalie.R
import com.panji.animalie.data.resource.Resource
import com.panji.animalie.databinding.FragmentMyPostBinding
import com.panji.animalie.model.response.PostResponse
import com.panji.animalie.ui.detail.ViewModelDetailPost
import com.panji.animalie.ui.fragments.adapter.PostAdapter
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPostFragment : Fragment(), ViewStateCallback<PostResponse> {

    private lateinit var binding: FragmentMyPostBinding
    private val viewModel: ViewModelMyPost by viewModels()
    private lateinit var adapterMyPost: PostAdapter
    private var userId: String = ""
    private var currentPage = 1
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
        binding = FragmentMyPostBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterMyPost = PostAdapter(
            context,
            type = "my_post",
            viewModel = ViewModelDetailPost(application = requireActivity().application),
            lifecycleOwner = viewLifecycleOwner
        )

        getMyPost()
        showRecycleView()
    }

    private fun getMyPost() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getMyPost(userId, currentPage)
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
            adapter = adapterMyPost
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
        adapterMyPost.submitList(data.posts.data)

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
        private const val KEY_USER_ID = "user_id"
        fun getInstance(userId: String): Fragment {
            return MyPostFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_USER_ID, userId)
                }
            }
        }
    }

}