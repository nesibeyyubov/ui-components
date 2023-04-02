package com.example.instavideos.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instavideos.GenericListAdapter
import com.example.instavideos.R
import com.example.instavideos.databinding.FragmentHorizontalMovingChipsBinding
import com.example.instavideos.databinding.ItemLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HorizontalMovingChipsFragment : Fragment(R.layout.fragment_horizontal_moving_chips) {

    private lateinit var binding: FragmentHorizontalMovingChipsBinding

    private val adapter by lazy {
        object : GenericListAdapter<ItemLayoutBinding, String>(
            inflate = { context: Context, viewGroup: ViewGroup, b: Boolean ->
                ItemLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    viewGroup,
                    false
                )
            },
            onBind = { item: String, index: Int, binding: ItemLayoutBinding ->
                binding.tvText.text = item
            }
        ) {
            override fun itemCount() = Int.MAX_VALUE
            override fun actualPosition(position: Int) = position % items.size
        }
    }

    private val items = listOf(
        "HTML", "CSS", "Java", "C#", "Javascript", "jQuery", "Kotlin", "Python", "Node JS"
    )

    private val scope = CoroutineScope(Dispatchers.Main)

    private val recyclerViews by lazy {
        listOf(
            binding.items1,
            binding.items2,
            binding.items3,
            binding.items4,
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHorizontalMovingChipsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        recyclerViews.forEachIndexed { index, recyclerView ->
            val reversed = index % 2 == 1
            val scrollPx = if (reversed) -1 else 1
            recyclerView.adapter = adapter

            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, reversed)
            recyclerView.layoutManager = layoutManager
            adapter.submitItems(items.shuffled())
            scope.launch {
                while (true) {
                    recyclerView.scrollBy(scrollPx, 0)
                    delay(10)
                }
            }
        }
    }

}