package com.android.searchproject.Adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.android.searchproject.MainActivity
import com.android.searchproject.data.DocumentsItem
import com.android.searchproject.databinding.ItemRecyclerviewSearchBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

//context도 SearchAdapter 파라미터에 추가
//이를 통하여 mainActivity에 있는 함수를 호출 가능해짐
//mainAcitvity에 addLike, remoteLike 라는 함수를 생성하여 Like가 눌리면 추가되고 없어지면 제거 되도록..
//mainActivity에서 arrayList를 작성, 이 arrayList를 보관함 Adapter에 넘겨줘야 함
class SearchAdapter(val mItems: MutableList<DocumentsItem>, val context: Context) :
    RecyclerView.Adapter<SearchAdapter.Holder>() {

    //ItemClick은 imageView 클릭 시 하트 생성할 때 사용
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //item_recyclerview_search.xml을 바인딩, Holder 객체 생성
        val binding = ItemRecyclerviewSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.imageView.setOnClickListener {  //클릭이벤트추가부분
            itemClick?.onClick(it, position)
            if (holder.heart.isInvisible) {
                //image 클릭 시 image 위에 heart가 없으면 heart가 보이도록(VISIBLE) 설정
                holder.heart.visibility = View.VISIBLE
                //MainActivity에 메소드 addLikeItem에 데이터 추가(image, site, date)
                (context as MainActivity)
                    .addLikeItem(
                        mItems[position].thumbnail_url.toString(),
                        mItems[position].display_sitename.toString(),
                        mItems[position].datetime.toString()
                    )
            } else if (holder.heart.isVisible) {
                //image 클릭 시 heart가 있으면 보이지 않도록 설정
                holder.heart.visibility = View.INVISIBLE
                (context as MainActivity)
                    .deleteLikeItem(
                        mItems[position].thumbnail_url.toString(),
                        mItems[position].display_sitename.toString(),
                        mItems[position].datetime.toString()
                    )
            }
        }

        //imageView에 thumbnail_url 가져오기
        Glide.with(holder.imageView.context).load(mItems[position].thumbnail_url)
            .into(holder.imageView)
        //textView에 사이트이름(sitename), 요일&날짜(datetime) 가져오기
        holder.site.text = mItems[position].display_sitename
//        holder.date.text = mItems[position].datetime
        val date = mItems[position].datetime
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val oldFormat = ZonedDateTime.parse(date, formatter)
        holder.date.text = oldFormat.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        //갯수는 SearchFragment에 size를 80으로 지정했는데 여길 80으로 지정해야하는건지 고민
        return mItems.size
    }


    inner class Holder(val binding: ItemRecyclerviewSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //Holder 생성, 변수 imageView, site, date에 각각 imageView, textView, textView로 초기화
        val imageView = binding.ivSearch
        val heart = binding.ivHeart
        val site = binding.tvSite
        val date = binding.tvDate
    }
}