package fts.ahmed.diaryapp;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fts.ahmed.diaryapp.databinding.RvItemListDiariesBinding
import fts.ahmed.diaryapp.pojo.Diary

class ListDiaryAdapter : RecyclerView.Adapter<ListDiaryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: RvItemListDiariesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Diary) {
            binding.tvTitle.text = item.title
            binding.tvTextBody.text = item.text
            if (item.alarm == null) binding.ivAlarm.setImageResource(0)
        }

    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Diary>() {
        override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvItemListDiariesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        // create this method which customize the view of single item
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}