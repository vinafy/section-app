package com.bangkit.section.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.section.R
import com.bangkit.section.ViewModelFactory
import com.bangkit.section.data.preference.UserPreference
import com.bangkit.section.databinding.FragmentProfileBinding
import com.bangkit.section.ui.auth.AuthActivity
import com.bumptech.glide.Glide

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel = ViewModelProvider(this, ViewModelFactory(requireContext())).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val sharedPref = UserPreference.initPref(requireContext(), "onSignIn")
        val avatarUrl = sharedPref.getString("avatarURL", null).toString()
        val name = sharedPref.getString("name", null).toString()
        val email = sharedPref.getString("email",null).toString()
        binding.emailProfile.setText(email)
        Glide.with(requireActivity())
            .load(avatarUrl)
            .fitCenter()
            .into(binding.avatarProfile)
        binding.nameProfile.setText(name)

        binding.btnLogout.setOnClickListener {
            profileViewModel.logout()
            showLogoutConfirmationDialog()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun logout(){
        UserPreference.logOut(requireContext())
        val intent = Intent(requireActivity(),AuthActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Konfirmasi Logout")
        builder.setMessage("Apakah Anda yakin ingin logout?")
        builder.setPositiveButton("Ya") { dialog, _ ->
            dialog.dismiss()
            logout()
        }
        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}