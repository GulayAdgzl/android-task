package com.android.android_task.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.android_task.R
import com.android.android_task.data.model.CharacterModel
import com.android.android_task.databinding.ItemDesignBinding

class CharacterAdapter(private val context: Context):
RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    class CharacterViewHolder(val itemCharacterBinding:ItemDesignBinding):RecyclerView.ViewHolder(itemCharacterBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem == newItem
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType:Int
    ) :CharacterViewHolder{
        val layoutInflater=LayoutInflater.from(parent.context)
        val itemDesignBinding=ItemDesignBinding.inflate(layoutInflater,parent,false)
        return  CharacterViewHolder(itemDesignBinding)
    }

    val differ=AsyncListDiffer(this,differCallback)
    override fun onBindViewHolder(holder:CharacterViewHolder,position:Int){
        val character=differ.currentList[position]
        holder.itemCharacterBinding.apply {
            tvTask.text= context.getString(R.string.task,character.task)
            tvDescription.text=context.getString(R.string.desc,character.description)
            tvTitle.text=context.getString(R.string.title,character.title)
            tvColorcode.text=context.getString(R.string.colorCode,character.colorCode)
            if(!character.colorCode.isNullOrEmpty()){
                root.setCardBackgroundColor(Color.parseColor(character.colorCode))
            }

        }
    }

    override fun getItemCount(): Int=differ.currentList.size




}