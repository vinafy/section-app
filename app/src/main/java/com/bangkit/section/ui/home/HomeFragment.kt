package com.bangkit.section.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.section.ReadArticleActivity
import com.bangkit.section.ViewModelFactory
import com.bangkit.section.data.Result
import com.bangkit.section.data.response.article.ListArticle
import com.bangkit.section.databinding.FragmentHomeBinding
import com.bangkit.section.ui.adapter.RecommendationAdapter
import com.bangkit.section.ui.article.category.CategoryActivity
import com.bangkit.section.ui.article.search.SearchActivity

class HomeFragment : Fragment(),RecommendationAdapter.OnAdapterClickCallback {
    private lateinit var recommendationAdapter: RecommendationAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this, ViewModelFactory(requireContext())).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        recommendationAdapter = RecommendationAdapter(this)
        binding.rvArticle.adapter = recommendationAdapter
        binding.rvArticle.layoutManager = LinearLayoutManager(requireContext())
        homeViewModel.getRecommendation("a").observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    val recommendationList = result.data.data
                    recommendationAdapter.setArticles(recommendationList)
                }
                is Result.Error -> {
                    val errorMessage = result.error
                }
                is Result.Loading -> {
                }
                else -> {}
            }
        }
        binding.searchBar.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }
        binding.KekerasanSeksual.setOnClickListener {
            Intent(requireContext(), CategoryActivity::class.java).also {
                it.putExtra(CategoryActivity.Category, "Kekerasan Seksual")
                startActivity(it)
            }
        }
        binding.KesehatanSeksual.setOnClickListener {
            Intent(requireContext(), CategoryActivity::class.java).also {
                it.putExtra(CategoryActivity.Category, "Kesehatan Seksual")
                startActivity(it)
            }
        }
        binding.PengetahuanSeksual.setOnClickListener {
            Intent(requireContext(), CategoryActivity::class.java).also {
                it.putExtra(CategoryActivity.Category, "Seksual")
                it.putExtra(CategoryActivity.TextCategory, "Pengetahuan Seksual")
                startActivity(it)
            }
        }
        binding.Psikologi.setOnClickListener {
            Intent(requireContext(), CategoryActivity::class.java).also {
                it.putExtra(CategoryActivity.Category, "Psikologi")
                startActivity(it)
            }
        }
        binding.PraktekSeksual.setOnClickListener {
            Intent(requireContext(), CategoryActivity::class.java).also {
                it.putExtra(CategoryActivity.Category, "Seksual")
                it.putExtra(CategoryActivity.TextCategory, "Praktek Seksual")
                startActivity(it)
            }
        }
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(data: ListArticle) {
        Intent(requireActivity(), ReadArticleActivity::class.java).also {
            it.putExtra(ReadArticleActivity.Article, data.Article)
            it.putExtra(ReadArticleActivity.Title, data.Title)
            startActivity(it)
        }
    }
}