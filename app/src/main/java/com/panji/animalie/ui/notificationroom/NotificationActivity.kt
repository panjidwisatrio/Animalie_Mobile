package com.panji.animalie.ui.notificationroom


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.panji.animalie.databinding.ActivityNotificationBinding
import com.panji.animalie.ui.adapter.NotificationAdapter

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private lateinit var notificationAdapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi adapter dan mengatur RecyclerView
        notificationAdapter = NotificationAdapter()
        binding.notificationItem.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity)
            adapter = notificationAdapter
        }

//        // Mendapatkan daftar notifikasi dari repository atau sumber data lainnya
//        val notificationList = repository.getNotifications()

//        // Mengirimkan daftar notifikasi ke adapter
//        notificationAdapter.submitList(notificationList)
    }
}
