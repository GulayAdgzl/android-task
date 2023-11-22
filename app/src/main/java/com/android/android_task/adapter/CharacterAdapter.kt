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
    override fun onBindViewHolder(holder:CharacterAdapter.CharacterViewHolder,position:Int){
        val character=characterList[position]
        holder.itemCharacterBinding.apply {
            tvTask.text=character.task
            tvDescription.text=character.description
            tvTitle.text=character.title
            tvColorcode.text=character.colorCode
            if(!character.colorCode.isNullOrEmpty()){
                root.setCardBackgroundColor(Color.parseColor(character.colorCode))
            }

        }
    }
    companion object DiffCallBack: DiffUtil.ItemCallback<CharacterModel>(){

        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem==newItem
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int=characterList.size




}