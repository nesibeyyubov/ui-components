package com.example.instavideos.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instavideos.Component
import com.example.instavideos.GenericListAdapter
import com.example.instavideos.R
import com.example.instavideos.components
import com.example.instavideos.databinding.ComponentLayoutBinding
import com.example.instavideos.databinding.FragmentHomeBinding
import com.example.instavideos.databinding.FragmentHorizontalMovingChipsBinding
import com.example.instavideos.databinding.ItemLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    private val adapter by lazy {
        GenericListAdapter<ComponentLayoutBinding, Component>(
            inflate = { context: Context, viewGroup: ViewGroup, b: Boolean ->
                ComponentLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    viewGroup,
                    false
                )
            },
            onBind = { component: Component, index: Int, binding: ComponentLayoutBinding ->
                binding.tvText.text = component.title

                binding.root.setOnClickListener {
                    findNavController().navigate(component.fragmentId)
                }
            }
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.rvComponents.adapter = adapter
        adapter.submitItems(components)


    }

}