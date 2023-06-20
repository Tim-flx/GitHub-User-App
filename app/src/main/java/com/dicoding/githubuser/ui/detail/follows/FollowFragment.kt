package com.dicoding.githubuser.ui.detail.follows

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.EmptyAdapter
import com.dicoding.githubuser.ListFollowAdapter
import com.dicoding.githubuser.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding

    private val followViewModel by lazy {
        ViewModelProvider(this)[FollowViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = requireArguments().getInt(ARG_POSITION)
        val username = requireArguments().getString(ARG_USERNAME)
        Log.d("FollowFragment", "position = $position, username = $username")

        if (position == 1) {
            if (username != null) {
                followViewModel.searchFollower(username)
            }
        } else {
            if (username != null) {
                followViewModel.searchFollowing(username)
            }
        }

        followViewModel.listFollow.observe(viewLifecycleOwner) { followData ->
            binding.rvProfile.adapter = ListFollowAdapter(followData)
        }
        followViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvProfile.layoutManager = layoutManager
        binding.rvProfile.adapter = EmptyAdapter()
        binding.rvProfile.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))
    }

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "username"
    }
}

