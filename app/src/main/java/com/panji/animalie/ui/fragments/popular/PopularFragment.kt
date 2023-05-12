package com.panji.animalie.ui.fragments.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.panji.animalie.databinding.FragmentPopularBinding
import com.panji.animalie.model.Post
import com.panji.animalie.util.ViewStateCallback

class PopularFragment : Fragment(), ViewStateCallback<List<Post>> {

    private lateinit var binding: FragmentPopularBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPopularBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getPopularPost()
        showRecycleView()
    }

    private fun getPopularPost() {
        // get data from viewmodel
    }

    private fun showRecycleView() {
        // inisialisasi adapter
    }

    override fun onSuccess(data: List<Post>) {
        // set data ke adapter
    }

    override fun onLoading() {
        // show loading
    }

    override fun onFailed(message: String?) {
        // show error
    }

    companion object {
        fun getInstance(): Fragment {
            return PopularFragment()
        }
    }

}