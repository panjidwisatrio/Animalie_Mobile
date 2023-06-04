package com.panji.animalie.ui.fragments.contact

import com.panji.animalie.ui.roomchat.RoomChatActivity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.panji.animalie.R
import com.panji.animalie.databinding.FragmentContactBinding
import com.panji.animalie.model.Doctor
import com.panji.animalie.ui.adapter.ContactAdapter

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding
    private lateinit var adapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactBinding.inflate(inflater, container, false)
        val view = binding.root

        val layoutManager = LinearLayoutManager(context)
        binding.itemContactList.layoutManager = layoutManager

        val doctorList = listOf(
            Doctor("James ucup", "Peternakan", "10", R.drawable.doctor_photo),
            Doctor("John udin", "Unggas", "2", R.drawable.doctor_photo),
            Doctor("John udin", "Unggas", "2", R.drawable.doctor_photo),
            Doctor("John udin", "Unggas", "2", R.drawable.doctor_photo),
            Doctor("John udin", "Unggas", "2", R.drawable.doctor_photo),
            Doctor("John udin", "Unggas", "2", R.drawable.doctor_photo),
            Doctor("John udin", "Unggas", "2", R.drawable.doctor_photo),
            Doctor("John udin", "Unggas", "2", R.drawable.doctor_photo)
        )

        adapter = ContactAdapter(doctorList)
        binding.itemContactList.adapter = adapter

        adapter.setOnItemClickListener(object : ContactAdapter.OnItemClickListener {
            override fun onItemClick(doctor: Doctor) {
                val intent = Intent(context, RoomChatActivity::class.java)
                intent.putExtra("doctorName", doctor.name)
                startActivity(intent)


            }
        })

        return view
    }

    companion object {
        fun getInstance(): ContactFragment {
            return ContactFragment()
        }
    }
}



















//
//
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.panji.animalie.R
//import com.panji.animalie.databinding.FragmentContactBinding
//import com.panji.animalie.model.Doctor
//import com.panji.animalie.ui.adapter.ContactAdapter

//class ContactFragment : Fragment() {
//
//    private lateinit var binding: FragmentContactBinding
//    private lateinit var adapter: ContactAdapter
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        binding = FragmentContactBinding.inflate(inflater, container, false)
//        val view = binding.root
//
//        val layoutManager = LinearLayoutManager(context)
//        binding.itemContactList.layoutManager = layoutManager
//
//        val doctorList = listOf(
//            Doctor("James ucup", "Peternakan", "10", R.drawable.doctor_photo),
//            Doctor("John udin", "Unggas", "2", R.drawable.doctor_photo),
//            Doctor("John udin", "Unggas", "2", R.drawable.doctor_photo),
//            Doctor("John udin", "Unggas", "2", R.drawable.doctor_photo),
//            Doctor("John udin", "Unggas", "2", R.drawable.doctor_photo),
//            Doctor("John udin", "Unggas", "2", R.drawable.doctor_photo),
//            Doctor("John udin", "Unggas", "2", R.drawable.doctor_photo),
//            Doctor("John udin", "Unggas", "2", R.drawable.doctor_photo)
//
//        )
//
//        adapter = ContactAdapter(doctorList)
//        binding.itemContactList.adapter = adapter
//
//        return view
//    }
//
//    companion object {
//        fun getInstance(): ContactFragment {
//            return ContactFragment()
//        }
//    }
//}
