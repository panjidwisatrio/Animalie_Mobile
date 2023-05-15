package com.panji.animalie.ui.fragments.latest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.panji.animalie.data.Resource
import com.panji.animalie.databinding.FragmentLatestBinding
import com.panji.animalie.model.response.PostResponse
import com.panji.animalie.ui.adapter.PostAdapter
import com.panji.animalie.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LatestFragment : Fragment(), ViewStateCallback<PostResponse> {

    private lateinit var binding: FragmentLatestBinding
    private val viewModel: ViewModelLatest by viewModels()
    private lateinit var adapterLatest: PostAdapter
    private var typePost: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typePost = it.getString(KEY_BUNDLE).toString()
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
        adapterLatest = PostAdapter()

        setVisibility()
        getPostLatest()
        showRecycleView()
    }

    private fun getPostLatest() {
        // get data from viewmodel
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getLatestPost(typePost, null, null).observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> onFailed(it.message)
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
        }
    }

    private fun setVisibility() {
        // set visibility
        binding.apply {
            recyclerView.visibility = visible
            progressBar.visibility = invisible
            errorText.visibility = invisible
        }
    }

    override fun onSuccess(data: PostResponse) {
        // set data ke adapter
        adapterLatest.submitList(data.posts.data)
        binding.apply {
            recyclerView.visibility = visible
            progressBar.visibility = invisible
            errorText.visibility = invisible
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
        fun getInstance(typePost: String): Fragment {
            return LatestFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, typePost)
                }
            }
        }
    }
}