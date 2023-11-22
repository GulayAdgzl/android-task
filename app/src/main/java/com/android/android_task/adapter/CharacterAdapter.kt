package com.android.android_task.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.android_task.data.model.CharacterModel
import com.android.android_task.databinding.ItemDesignBinding

class CharacterAdapter(private var characterList:List<CharacterModel>):
RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    class CharacterViewHolder(val itemCharacterBinding:ItemDesignBinding):RecyclerView.ViewHolder(itemCharacterBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType:Int
    ) :CharacterViewHolder{
        val layoutInflater=LayoutInflater.from(parent.context)
        val itemDesignBinding=ItemDesignBinding.inflate(layoutInflater,parent,false)
        return  CharacterViewHolder(itemDesignBinding)
    }



}