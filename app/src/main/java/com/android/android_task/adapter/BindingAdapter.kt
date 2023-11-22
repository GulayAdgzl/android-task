package com.android.android_task.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.android_task.data.model.CharacterModel

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,data:List<CharacterModel>?){
    val adapter=recyclerView.adapter as CharacterAdapter
    listOf(adapter)
}