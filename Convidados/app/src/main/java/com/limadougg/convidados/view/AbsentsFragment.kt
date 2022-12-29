package com.limadougg.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.limadougg.convidados.constants.DataBaseConstants
import com.limadougg.convidados.databinding.FragmentAbsentsBinding
import com.limadougg.convidados.view.adapter.GuestsAdapter
import com.limadougg.convidados.view.listener.OnGuestListener
import com.limadougg.convidados.viewmodel.GuestsViewModel

class AbsentsFragment : Fragment() {

    private var _binding: FragmentAbsentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GuestsViewModel
    private val adapter = GuestsAdapter()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, b: Bundle? ): View {
        viewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        _binding = FragmentAbsentsBinding.inflate(inflater, container, false)

        binding.recyclerGuests.layoutManager = LinearLayoutManager(context)
        binding.recyclerGuests.adapter = adapter

        val listener = object : OnGuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(DataBaseConstants.GUEST.ID, id)

                intent.putExtras(bundle)

                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.getAbsent()
            }
        }

        adapter.attachListener(listener)
        observe()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAbsent()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.guests.observe(viewLifecycleOwner) {
            adapter.updatedGuests(it)
        }
    }
}