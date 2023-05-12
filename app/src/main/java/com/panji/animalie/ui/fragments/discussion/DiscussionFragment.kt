package com.panji.animalie.ui.fragments.discussion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.panji.animalie.databinding.FragmentDiscussionBinding
import com.panji.animalie.model.response.PostResponse
import com.panji.animalie.util.ViewStateCallback

class DiscussionFragment : Fragment(), ViewStateCallback<PostResponse> {

    private lateinit var binding: FragmentDiscussionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding  = FragmentDiscussionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDiscussionPost()
        showRecycleView()
    }

    private fun getDiscussionPost() {
        // get data from viewmodel
    }

    private fun showRecycleView() {
        // inisialisasi adapter
    }

    override fun onSuccess(data: PostResponse) {
        // set data to adapter
    }

    override fun onLoading() {
        // show loading
    }

    override fun onFailed(message: String?) {
        // show error
    }

    companion object {
        fun getInstance(): Fragment {
            return DiscussionFragment()
        }
    }
}