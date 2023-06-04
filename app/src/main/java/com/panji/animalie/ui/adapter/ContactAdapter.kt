package com.panji.animalie.ui.adapter
//n
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.panji.animalie.databinding.ContactCardBinding
import com.panji.animalie.model.Doctor

//
class ContactAdapter(private val doctorList: List<Doctor>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var onItemClickListener: ((Doctor) -> Unit)? = null
    private var selectedItemPosition: Int = RecyclerView.NO_POSITION
    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContactCardBinding.inflate(inflater, parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val doctor = doctorList[position]
        holder.bind(doctor)
    }

    override fun getItemCount(): Int = doctorList.size

    fun setOnItemClickListener(listener: (Doctor) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    inner class ContactViewHolder(private val binding: ContactCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bind(doctor: Doctor) {
            binding.apply {
                doctorName.text = doctor.name
                specialistDoctor.text = doctor.specialist
                numberYears.text = doctor.workExperience.toString()
                binding.doctorPhoto.setImageResource(doctor.doctorphoto)

                root.setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            v.alpha = 0.5f // Ubah alpha menjadi 0.5 saat disentuh
                            selectedItemPosition = adapterPosition
                        }
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            v.alpha = 1f // Kembalikan alpha ke 1 setelah tidak disentuh atau batal
                        }
                    }
                    false
                }

                binding.root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val doctor = doctorList[position]
                        itemClickListener?.onItemClick(doctor)
                    }
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(doctor: Doctor)
    }
}






















//
//
//
//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.MotionEvent
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.panji.animalie.databinding.ContactCardBinding
//import com.panji.animalie.model.Doctor




//class ContactAdapter(private val doctorList: List<Doctor>) :
//    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
//
//    private var onItemClickListener: ((Doctor) -> Unit)? = null
//    private var selectedItemPosition: Int = RecyclerView.NO_POSITION
//
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ContactCardBinding.inflate(inflater, parent, false)
//        return ContactViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
//        val doctor = doctorList[position]
//        holder.bind(doctor)
//
//        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(doctor)
//        }
//    }
//
//    override fun getItemCount(): Int = doctorList.size
//
//    fun setOnItemClickListener(listener: (Doctor) -> Unit) {
//        onItemClickListener = listener
//    }
//
//    inner class ContactViewHolder(private val binding: ContactCardBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        @SuppressLint("ClickableViewAccessibility")
//        fun bind(doctor: Doctor) {
//            binding.apply {
//                doctorName.text = doctor.name
//                specialistDoctor.text = doctor.specialist
//                numberYears.text = doctor.workExperience.toString()
//                binding.doctorPhoto.setImageResource(doctor.doctorphoto)
//
//                root.setOnTouchListener { v, event ->
//                    when (event.action) {
//                        MotionEvent.ACTION_DOWN -> {
//                            v.alpha = 0.5f // Ubah alpha menjadi 0.5 saat disentuh
//                            selectedItemPosition = adapterPosition
//                        }
//                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
//                            v.alpha = 1f // Kembalikan alpha ke 1 setelah tidak disentuh atau batal
//                        }
//                    }
//                    false
//                }
//            }
//        }
//    }


































//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.MotionEvent
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.panji.animalie.databinding.ContactCardBinding
//import com.panji.animalie.model.Doctor
//
//class ContactAdapter(private val doctorList: List<Doctor>) :
//    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
//
//    private var onItemClickListener: ((Doctor) -> Unit)? = null
//    private var selectedItemPosition: Int = RecyclerView.NO_POSITION
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ContactCardBinding.inflate(inflater, parent, false)
//        return ContactViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
//        val doctor = doctorList[position]
//        holder.bind(doctor)
//
//        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(doctor)
//        }
//    }
//
//    override fun getItemCount(): Int = doctorList.size
//
//    fun setOnItemClickListener(listener: (Doctor) -> Unit) {
//        onItemClickListener = listener
//    }
//
//    inner class ContactViewHolder(private val binding: ContactCardBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        @SuppressLint("ClickableViewAccessibility")
//        fun bind(doctor: Doctor) {
//            binding.apply {
//                doctorName.text = doctor.name
//                specialistDoctor.text = doctor.specialist
//                numberYears.text = doctor.workExperience.toString()
//                binding.doctorPhoto.setImageResource(doctor.doctorphoto)
//
//                root.setOnTouchListener { v, event ->
//                    when (event.action) {
//                        MotionEvent.ACTION_DOWN -> {
//                            v.alpha = 0.5f // Ubah alpha menjadi 0.5 saat disentuh
//                            selectedItemPosition = adapterPosition
//                        }
//                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
//                            v.alpha = 1f // Kembalikan alpha ke 1 setelah tidak disentuh atau batal
//                        }
//                    }
//                    false
//                }
//            }
//        }
//    }
//}
