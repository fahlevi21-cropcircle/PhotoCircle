package com.cropcircle.photocircle.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.cropcircle.photocircle.MainActivity
import com.cropcircle.photocircle.R
import com.cropcircle.photocircle.databinding.HomeFragmentBinding
import com.cropcircle.photocircle.model.PhotoItem
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.details_fragment.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment), HomePagingAdapter.OnItemClickListener {

    //private lateinit var viewModel: HomeViewModel
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomePagingAdapter
    private val viewModel by viewModels<HomeViewModel>()

    private val latestAdapter = LatestPhotoRecyclerAdapter()
    private val popularAdapter = PopularPhotoRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        _binding = HomeFragmentBinding.bind(view)

        postponeEnterTransition()

        /* val navController = NavHostFragment.findNavController(this)
         val appBarConfiguration = AppBarConfiguration(navController.graph)
         NavigationUI.setupWithNavController(binding.mainToolbar, navController, appBarConfiguration)*/
        //(activity as MainActivity).setSupportActionBar(binding.mainToolbar)
        //(activity as MainActivity).title = null
        (activity as MainActivity).setupNavComponents(false)
        //(activity as MainActivity).title = "Latest Photos"

        /*adapter = HomePagingAdapter(this)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS*/

        latestAdapter.onItemClickListener(
            object : LatestPhotoRecyclerAdapter.OnItemClickListener{
                override fun onClick(item: PhotoItem, view: View, position: Int) {
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item.id)
                    findNavController().navigate(action)
                }
            }
        )

        popularAdapter.onItemClickListener(
            object : PopularPhotoRecyclerAdapter.OnItemClickListener{
                override fun onClick(item: PhotoItem, view: View, position: Int) {
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item.id)
                    findNavController().navigate(action)
                }
            }
        )

        binding.apply {
            pagerLatestPhoto.offscreenPageLimit = 2
            pagerLatestPhoto.adapter = latestAdapter
            pagerLatestPhoto.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            popularRc.adapter = popularAdapter
            popularRc.setHasFixedSize(true)
            popularRc.layoutManager = LinearLayoutManager(context)
        }

        /*binding.apply {
            mainToolbar.setNavigationIcon(R.drawable.ic_baseline_photo)
            rcHome.setHasFixedSize(true)
            rcHome.layoutManager = layoutManager
            *//*rcHome.addItemDecoration(
                LatestPhotoItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.small_layout_margin),
                    resources.getDimensionPixelSize(R.dimen.small_layout_margin),
                    0,
                    resources.getDimensionPixelSize(R.dimen.margin_8),
                    resources,
                    ItemLayout.STAGGERED_TWO_SPAN
                )
            )*//*
            rcHome.adapter = adapter.withLoadStateHeaderAndFooter(
                header = HomeLoadStateAdapter { adapter.retry() },
                footer = HomeLoadStateAdapter { adapter.retry() }
            )
            homeBtnRetry.setOnClickListener {
                adapter.retry()
            }
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
        }*/

        /*val queries = mutableMapOf<String, String>()
        queries["order_by"] = "latest"
        viewModel.setQueries(queries)*/

        observeLatestPhoto()
        observePopularPhoto()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)

        val searchItem = menu.findItem(R.id.action_menu_main_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    //do nothing
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        //binding.rcHome.scrollToPosition(0)
                        viewModel.search(query)
                        searchPhoto()
                        searchView.clearFocus()
                    }
                    return true
                }
            },
        )
    }

    private fun observeLatestPhoto() {
        viewModel.latestPhotos.observe(viewLifecycleOwner) {
            if (it != null){
                view?.doOnPreDraw {
                    startPostponedEnterTransition()
                }
                latestAdapter.setList(it)
            }
            //adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun observePopularPhoto() {
        viewModel.popularPhotos.observe(viewLifecycleOwner) {
            /*if (it != null){
                view?.doOnPreDraw {
                    startPostponedEnterTransition()
                }
            }*/
            //adapter.submitData(viewLifecycleOwner.lifecycle, it)
            popularAdapter.setList(it)
        }
    }

    private fun searchPhoto() {
        viewModel.searchedPhotos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(id: String, cardView: MaterialCardView) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id)
        val transitionName = getString(R.string.shared_transition_name)
        val extras = FragmentNavigatorExtras(cardView to transitionName)
        findNavController().navigate(action, extras)
    }

}