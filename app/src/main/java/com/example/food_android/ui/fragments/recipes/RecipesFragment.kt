package com.example.food_android.ui.fragments.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_android.MainViewModel
import com.example.food_android.adapter.RecipesAdapter
import com.example.food_android.databinding.FragmentRecipesBinding
import com.example.food_android.util.Constants.Companion.API_KEY
import com.example.food_android.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private val mAdapter by lazy { RecipesAdapter() }
    private lateinit var mView: FragmentRecipesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = FragmentRecipesBinding.inflate(layoutInflater)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        setupRecyclerView()
        requestApiData()
        return mView.root
    }

    private fun requestApiData() {
        mainViewModel.getRecipes(applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { mAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {

                }
            }
        }
    }

    private fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String,String> = HashMap()

        queries["number"] = "50"
        queries["apiKey"] = API_KEY
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries
    }

    private fun setupRecyclerView() {
        mView.recyclerview.adapter = mAdapter
        mView.recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

}