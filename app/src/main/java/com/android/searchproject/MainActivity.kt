package com.android.searchproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.android.searchproject.Fragment.LockerFragment
import com.android.searchproject.Fragment.SearchFragment
import com.android.searchproject.data.MainActivityItem
import com.android.searchproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //SearchFragment의 데이터 형식(image, site, date)
    val fragment = mutableListOf<MainActivityItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setFragment(SearchFragment())

        binding.apply {
            btnSearchFragment.setOnClickListener {
                setFragment(SearchFragment())
            }
            btnLockerFragment.setOnClickListener {
                setFragment(LockerFragment())
            }
        }

    }

    private fun setFragment(frag: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frameLayout, frag)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

    fun addLikeItem(image: String, site: String, date: String) {
        val item = MainActivityItem(image, site, date)
        fragment.add(item)
    }

    fun deleteLikeItem(image: String, site: String, date: String) {
        val item = MainActivityItem(image, site, date)
        fragment.remove(item)
    }

}