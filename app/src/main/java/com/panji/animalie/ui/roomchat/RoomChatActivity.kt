package com.panji.animalie.ui.roomchat



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.panji.animalie.R
import com.panji.animalie.databinding.ActivityRoomChatBinding
import com.panji.animalie.ui.adapter.RoomChatAdapter

class RoomChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomChatBinding
    private lateinit var viewModel: RoomChatViewModel
    private lateinit var adapter: RoomChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RoomChatViewModel::class.java)
        adapter = RoomChatAdapter()

        binding.itemChatBubble.layoutManager = LinearLayoutManager(this)
        binding.itemChatBubble.adapter = adapter

        viewModel.messages.observe(this) { messages ->
            adapter.setMessages(messages)
            binding.itemChatBubble.scrollToPosition(messages.size - 1)
        }

        binding.SendMessageButton.setOnClickListener {
            val content = binding.writeTextMessage.text.toString().trim()
            if (content.isNotEmpty()) {
                viewModel.sendMessage(content, R.drawable.testingphoto)
                binding.writeTextMessage.setText("")
            }
        }


        val doctorName = intent.getStringExtra("doctorName")
        // Lakukan operasi lain yang diperlukan dengan doctorName, seperti menampilkan di ActionBar
        binding.receiverName.text = doctorName
        supportActionBar?.title = doctorName
    }
}









//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.panji.animalie.R
//import com.panji.animalie.databinding.ActivityRoomChatBinding
//import com.panji.animalie.model.Message
//import com.panji.animalie.model.RoomChat
//import com.panji.animalie.ui.adapter.RoomChatAdapter
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//
//class RoomChatActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityRoomChatBinding
//    private lateinit var roomChatAdapter: RoomChatAdapter
//    private lateinit var roomChat: RoomChat
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityRoomChatBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setupRecyclerView()
//
//        // Create a new RoomChat object
//        roomChat = RoomChat(
//            sender = "John", // Replace with the actual sender name
//            senderProfilePhoto = R.drawable.testingphoto, // Replace with the actual sender profile photo
//            receiver = "Emily", // Replace with the actual receiver name
//            receiverProfilePhoto = R.drawable.doctor_photo,
//            time = getCurrentTime()// Replace with the actual receiver profile photo
//        )
//
//        // Add some sample messages to the roomChat object
//        roomChat.messages = mutableListOf(
//            Message("John", (R.drawable.testingphoto), "Hsii", getCurrentTime(),false),
//
//        )
//
//        // Create an instance of the RoomChatAdapter and pass the roomChat object to it
//        roomChatAdapter = RoomChatAdapter(roomChat)
//
//        // Set the adapter to the RecyclerView
//        binding.itemChatBubble.adapter = roomChatAdapter
//    }
//
//    private fun setupRecyclerView() {
//        binding.itemChatBubble.layoutManager = LinearLayoutManager(this)
//    }
//
//    private fun getCurrentTime(): String {
//        // Return the current time as a string
//        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
//    }
//}
