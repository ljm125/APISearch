package com.android.searchproject.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.android.searchproject.Adapter.SearchAdapter
import com.android.searchproject.data.DocumentsItem
import com.android.searchproject.data.NetWorkClient
import com.android.searchproject.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate((layoutInflater)) }
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapter: SearchAdapter

    //변수 items는 리스트 형식, DocumentsItem형, 데이터 원본을 Internet을 통해 가져와서 직접 작성은 안 함
    var items = mutableListOf<DocumentsItem>()

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
        adapter = SearchAdapter(items, requireContext())
        binding.apply {
            rvSearch.adapter = adapter
            rvSearch.layoutManager = GridLayoutManager(context, 2)
        }
        Log.d("onViewCreated", "Created test")
        binding.btnSearch.setOnClickListener {
            Log.d("onViewCreated", "click")
            val key = binding.etSearch.text.toString()
            saveSearchData()
            commuicateNetWork(key)
            softkeyboardHide()
        }
        loadSearchData()
    }

    fun saveSearchData() {
        val save = requireContext().getSharedPreferences("save", 0)
        val edit = save.edit()
        edit.putString("search", binding.etSearch.text.toString())
        edit.apply()
    }

    fun loadSearchData() {
        val load = requireContext().getSharedPreferences("save", 0)
        binding.etSearch.setText(load.getString("search", ""))
    }

    private fun commuicateNetWork(key: String) = lifecycleScope.launch() {
        val authHeader = "KakaoAK f0ec90fdd19fc6925133ccfdcd8f85df"
        val sort = "accuracy"
        val page = 1
        val size = 80
        items.clear()
        val responseData = NetWorkClient.searchNetWork.getSearch(authHeader, key, sort, page, size)
        responseData.searchDocuments?.let { items.addAll(it) }
        adapter.notifyDataSetChanged()
    }

    //키보드를 숨김
    fun softkeyboardHide() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}