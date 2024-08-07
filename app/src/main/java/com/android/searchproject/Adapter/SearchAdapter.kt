package com.android.searchproject.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.searchproject.data.DocumentsItem
import com.android.searchproject.databinding.ItemRecyclerviewSearchBinding
import com.bumptech.glide.Glide
import okhttp3.internal.format
import java.text.Format
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class SearchAdapter(val mItems:MutableList<DocumentsItem>):RecyclerView.Adapter<SearchAdapter.Holder>() {

    //ItemClick은 imageView 클릭 시 하트 생성할 때 사용
    interface ItemClick {
        fun onClick(view : View, position : Int)
    }

    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //item_recyclerview_search.xml을 바인딩, Holder 객체 생성
        val binding = ItemRecyclerviewSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
//        holder.itemView.setOnClickListener {  //클릭이벤트추가부분
//            itemClick?.onClick(it, position)
//        }

        //imageView에 thumbnail_url 가져오기
        Glide.with(holder.imageView.context) .load(mItems[position].thumbnail_url).into(holder.imageView)

        //textView에 사이트이름(sitename), 요일&날짜(datetime) 가져오기
        //요일&날짜 같은 경우 DateFormat을 통해 String 형식으로 변환
        holder.site.text = mItems[position].display_sitename
        val dates = mItems[position].datetime
//        val formatType = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        holder.date.text = dates?.format(formatType)
        holder.date.text = mItems[position].datetime
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        //갯수는 SearchFragment에 size를 80으로 지정했는데 여길 80으로 지정해야하는건지 고민
        return mItems.size
    }

    inner class Holder(val binding: ItemRecyclerviewSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        //Holder 생성, 변수 imageView, site, date에 각각 imageView, textView, textView로 초기화
        val imageView = binding.ivSearch
        val heart = binding.ivHeart
        val site = binding.tvSite
        val date = binding.tvDate
    }
}