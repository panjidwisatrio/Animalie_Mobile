package com.panji.animalie.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.panji.animalie.R
import com.panji.animalie.databinding.FragmentLatestBinding
import com.panji.animalie.model.Post
import com.panji.animalie.ui.adapter.PostAdapter

class LatestFragment : Fragment() {

    private lateinit var binding: FragmentLatestBinding
    private lateinit var adapter: PostAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLatestBinding.inflate(layoutInflater)

        //get and send post data to ui
//        with(binding.latestRecycler) {
//            adapter = PostAdapter(getData())
//            setHasFixedSize(true)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_latest, container, false)
    }

    private fun getData(): List<Post> {
        return listOf(
            Post(
                1,
                1,
                1,
                "This Post Title",
                "this-post-title",
                "Lorem ipsum are Post content, dolor sit amet, consectetur adipiscing elit. Donec vestibulum tellus et ex tristique, non suscipit urna gravida. Sed nec interdum elit. Vestibulum sit amet felis ac tortor viverra ultrices. Pellentesque eget nibh ipsum",
                "01-01-2023",
                "01-01-2023"
            )
        )
    }
}