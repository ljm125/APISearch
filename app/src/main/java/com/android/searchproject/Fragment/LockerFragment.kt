package com.android.searchproject.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.android.searchproject.Adapter.LockerAdapter
import com.android.searchproject.MainActivity
import com.android.searchproject.databinding.FragmentLockerBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LockerFragment : Fragment() {
    private val binding by lazy { FragmentLockerBinding.inflate(layoutInflater) }
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapter: LockerAdapter
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as MainActivity
        adapter = LockerAdapter(mainActivity.fragment, requireContext())
        binding.apply {
            rvLocker.adapter = adapter
            rvLocker.layoutManager = GridLayoutManager(context, 2)
        }
        adapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LockerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}