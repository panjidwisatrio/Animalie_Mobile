package com.panji.animalie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.panji.animalie.databinding.CertificateBinding
import com.panji.animalie.model.Certificate

class CertificateAdapter(private val data: List<Certificate>) :
    RecyclerView.Adapter<CertificateAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CertificateBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(
        private val binding: CertificateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(certificate: Certificate) = with(binding) {

//            temporary change
            certificateTitle.text = certificate.name
            certificatePublisher.text = certificate.organization_issue
        }
    }
}

