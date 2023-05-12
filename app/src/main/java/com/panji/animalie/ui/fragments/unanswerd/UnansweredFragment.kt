package com.panji.animalie.ui.fragments.unanswerd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.panji.animalie.databinding.FragmentUnansweredBinding
import com.panji.animalie.model.Post
import com.panji.animalie.util.ViewStateCallback

class UnansweredFragment : Fragment(), ViewStateCallback<List<Post>> {

    private lateinit var binding: FragmentUnansweredBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUnansweredBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getUnanswerdPost()
        showRecycleView()
    }

    private fun getUnanswerdPost() {
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
            return UnansweredFragment()
        }
    }
}