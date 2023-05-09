package com.panji.animalie.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.panji.animalie.databinding.FragmentPopularBinding
import com.panji.animalie.model.Post
import com.panji.animalie.ui.adapter.PostAdapter

class PopularFragment : Fragment() {

    private  lateinit var binding: FragmentPopularBinding
    private lateinit var myAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPopularBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        myAdapter = PostAdapter(getPopular())
        with(binding.recyclerView) {
            adapter = myAdapter
            setHasFixedSize(true)
        }
    }

    private fun getPopular(): List<Post>{
        return listOf(
            Post(
                2,
                1,
                1,
                "Popular Post Title",
                "this-post-title",
                "Lorem ipsum are Post content, dolor sit amet, consectetur adipiscing elit. Donec vestibulum tellus et ex tristique, non suscipit urna gravida. Sed nec interdum elit. Vestibulum sit amet felis ac tortor viverra ultrices. Pellentesque eget nibh ipsum",
                "01-01-2023",
                "01-01-2023"
            )
        )
    }
    
}