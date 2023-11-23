package com.android.android_task.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.android_task.data.model.CharacterModel
import com.android.android_task.databinding.ItemDesignBinding

class CharacterAdapter(context: Context):
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

    val differ=AsyncListDiffer(this,DiffCallBack)
    override fun onBindViewHolder(holder:CharacterAdapter.CharacterViewHolder,position:Int){
       // val character=characterList[position]
        val character=differ.currentList[position]
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
    companion object DiffCallBack : DiffUtil.ItemCallback<CharacterModel>() {

        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem == newItem // Değiştirilecek olan, itemId'yi benzerlik kontrolüyle değiştirin
        }

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem == newItem
        }

        fun calculateDiffCallback(oldList: List<CharacterModel>, newList: List<CharacterModel>): DiffUtil.Callback {
            return object : DiffUtil.Callback() {
                override fun getOldListSize(): Int = oldList.size
                override fun getNewListSize(): Int = newList.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return areItemsTheSame(oldList[oldItemPosition], newList[newItemPosition])
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return areContentsTheSame(oldList[oldItemPosition], newList[newItemPosition])
                }
            }
        }
    }


    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int=differ.currentList.size




}