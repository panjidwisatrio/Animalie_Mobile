package com.panji.animalie.ui.fragments.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.materialswitch.MaterialSwitch
import com.panji.animalie.data.datastore.ThemePreferences
import com.panji.animalie.databinding.FragmentSettingBinding
import kotlinx.coroutines.launch

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private lateinit var themeToggle: MaterialSwitch


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)


        //top app bar
        val topAppBar = binding.settingToolbar.appBar

        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(topAppBar)

        // Customize the action bar
        val actionBar = appCompatActivity.supportActionBar
        actionBar?.apply {
            title = "Settings"
            setDisplayHomeAsUpEnabled(true)
        }

        // Handle the back button click
        topAppBar.setNavigationOnClickListener {
            navigateToActivity()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //checkTheme()
        //setToggleTheme()

    }

    private fun checkTheme() {
        //checked state accordingly
        lifecycleScope.launch {
            ThemePreferences.getTheme(requireContext()).collect { isDarkTheme ->
                binding.themeToggle.isChecked = isDarkTheme
                if (isDarkTheme) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }

    private fun setToggleTheme() {
        themeToggle = binding.themeToggle

        // Set the OnCheckedChangeListener
        themeToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            lifecycleScope.launch {
                if (isChecked) {
                    // Dark theme selected
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    ThemePreferences.setTheme(requireContext(), true)
                } else {
                    // Light theme selected
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    ThemePreferences.setTheme(requireContext(), false)
                }
            }
            buttonView.isChecked = isChecked
        }
    }

    private fun navigateToActivity() {
        activity?.supportFragmentManager?.popBackStack()
    }
}