package com.android.searchproject.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.searchproject.MainActivity
import com.android.searchproject.data.MainActivityItem
import com.android.searchproject.databinding.ItemRecyclerviewLockerBinding
import com.bumptech.glide.Glide

class LockerAdapter(val mItems: MutableList<MainActivityItem>, val context: Context): RecyclerView.Adapter<LockerAdapter.Holder>() {

    //ItemClick은 imageView 클릭 시 하트 생성할 때 사용
    interface ItemClick {
        fun onClick(view : View, position : Int)
    }

    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //item_recyclerview_locker.xml을 바인딩, Holder 객체 생성
        val binding = ItemRecyclerviewLockerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val main = (context as MainActivity).fragment
        Glide.with(holder.imageView.context).load(main.get(position).thumbnail_url).into(holder.imageView)
        holder.site.text = main.get(position).display_sitename
        holder.date.text = main.get(position).datetime

        holder.imageView.setOnClickListener {  //클릭이벤트추가부분
            itemClick?.onClick(it, position)
            notifyDataSetChanged()
            (context as MainActivity).deleteLikeItem(
                mItems[position].thumbnail_url.toString(),
                mItems[position].display_sitename.toString(),
                mItems[position].datetime.toString())
        }


    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class Holder(val binding: ItemRecyclerviewLockerBinding) : RecyclerView.ViewHolder(binding.root) {
        //Holder 생성, 변수 imageView, site, date에 각각 imageView, textView, textView로 초기화
        val imageView = binding.ivLocker
        val site = binding.tvSiteLocker
        val date = binding.tvDateLocker
    }
}