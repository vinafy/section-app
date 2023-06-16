package com.bangkit.section.ui.forum

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.section.ReadArticleActivity
import com.bangkit.section.ViewModelFactory
import com.bangkit.section.data.preference.UserPreference
import com.bangkit.section.data.response.forum.ListForum
import com.bangkit.section.databinding.FragmentForumBinding
import com.bangkit.section.ui.adapter.ArticleAdapter
import com.bangkit.section.ui.adapter.ForumAdapter
import com.bangkit.section.ui.forum.comment.CommentActivity
import com.bangkit.section.ui.forum.posting.PostingActivity
import com.bangkit.section.ui.home.HomeViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class ForumFragment : Fragment() {

    private var _binding: FragmentForumBinding? = null
    private val binding get() = _binding!!
    private lateinit var forumAdapter: ForumAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForumBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val sharedPref = UserPreference.initPref(requireContext(), "onSignIn")
        val avatarUrl = sharedPref.getString("avatarURL", null).toString()
        Glide.with(requireActivity())
            .load(avatarUrl)
            .fitCenter()
            .into(binding.avatar)
        forumAdapter = ForumAdapter()

        binding.createDiscussion.setOnClickListener {
            val intent = Intent(requireActivity(),PostingActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val ForumViewModel = ViewModelProvider(this, ViewModelFactory(requireContext()))[ForumViewModel::class.java]
        binding.rvPosting.adapter = forumAdapter
        binding.rvPosting.layoutManager =LinearLayoutManager(requireContext())
        ForumViewModel.getStories().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                forumAdapter.submitData(viewLifecycleOwner.lifecycle , data)
                Log.e(TAG, "onViewCreated: $data", )
            }
        }
        forumAdapter.setOnItemClickCallback(object : ForumAdapter.OnAdapterClickCallback{
            override fun onItemClick(data: ListForum) {
                val intent = Intent(requireActivity(), CommentActivity::class.java)
                startActivity(intent)
            }

        })
        binding.avatar.setOnClickListener {
            val intent = Intent(requireContext(),ForumActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}