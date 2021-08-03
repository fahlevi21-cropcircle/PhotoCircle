package com.cropcircle.photocircle.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cropcircle.photocircle.MainActivity
import com.cropcircle.photocircle.R
import com.cropcircle.photocircle.databinding.HomeFragmentBinding
import com.cropcircle.photocircle.model.ItemLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*

@AndroidEntryPoint
class HomeFragment : Fragment(), HomePagingAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    //private lateinit var viewModel: HomeViewModel
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = HomeFragmentBinding.bind(view)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.mainToolbar.setupWithNavController(navController,appBarConfiguration)
        //(activity as MainActivity).title = "Latest Photos"

        val adapter = HomePagingAdapter(this)

        binding.apply {
            binding.rcHome.layoutManager = GridLayoutManager(context, 2)
            rcHome.addItemDecoration(
                LatestPhotoItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.small_layout_margin),
                    resources.getDimensionPixelSize(R.dimen.small_layout_margin),
                    0,
                    resources.getDimensionPixelSize(R.dimen.margin_8),
                    resources,
                    ItemLayout.GRID_TWO_SPAN
                )
            )
            rcHome.adapter = adapter.withLoadStateHeaderAndFooter(
                header = HomeLoadStateAdapter { adapter.retry() },
                footer = HomeLoadStateAdapter { adapter.retry() }
            )
        }

        adapter.addLoadStateListener {
            binding.apply {
                homeLoadingBar.isVisible = it.source.refresh is LoadState.Loading
                rcHome.isVisible = it.source.refresh is LoadState.NotLoading
                homeBtnRetry.isVisible = it.source.refresh is LoadState.Error
                homeTextError.isVisible = it.source.refresh is LoadState.Error

                if (it.source.refresh is LoadState.NotLoading &&
                    it.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    rcHome.isVisible = false
                    homeTextEmpty.isVisible = true
                } else {
                    homeTextEmpty.isVisible = false
                }
            }
        }

        val queries = mutableMapOf<String, String>()
        queries["order_by"] = "latest"
        viewModel.setQueries(queries)

        viewModel.latestPhotos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(id: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id)
        findNavController().navigate(action)
    }

}