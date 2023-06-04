package com.panji.animalie.ui.fragments.messagehistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.panji.animalie.R
import com.panji.animalie.databinding.FragmentMessageHistoryBinding
import com.panji.animalie.model.MessageHistory
import com.panji.animalie.ui.adapter.MessageHistoryAdapter


class MessageHistoryFragment : Fragment() {

    private lateinit var binding: FragmentMessageHistoryBinding
    private lateinit var adapter: MessageHistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMessageHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MessageHistoryAdapter(getDummyData()) // Menggunakan data dummy
        binding.itemMessageHistoryList.adapter = adapter
        binding.itemMessageHistoryList.layoutManager = LinearLayoutManager(activity)
    }
//
    private fun getDummyData(): List<MessageHistory> {
        // Data dummy
        val dummyData = mutableListOf<MessageHistory>()
        dummyData.add(MessageHistory(R.drawable.testingphoto, "John Doe", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,"))
        dummyData.add(MessageHistory(R.drawable.testingphoto, "Jane Smith", "Hi there!"))
        dummyData.add(MessageHistory(R.drawable.testingphoto, "David Johnson", "How are you?"))
        return dummyData
    }

    companion object {
        fun getInstance(): MessageHistoryFragment {
            return MessageHistoryFragment()
        }
    }
}
