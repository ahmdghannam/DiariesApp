package fts.ahmed.diaryapp;

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fts.ahmed.diaryapp.databinding.RvItemListDiariesBinding
import fts.ahmed.diaryapp.pojo.Diary


class ListDiaryAdapter(
    val context: Context,
    val onClick: (String, String) -> Unit,
    val deleteDiary: (Diary) -> Unit
) : RecyclerView.Adapter<ListDiaryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: RvItemListDiariesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private fun alertDialog(diary: Diary): Boolean {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage("Delete the Diary ?")
            builder.setCancelable(true)

            var heIsSure = false

            builder.setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { dialog, id ->
                    heIsSure = true
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                    deleteDiary(diary)
                    dialog.cancel()
                })

            builder.setNegativeButton(
                "No",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

            val alert: AlertDialog = builder.create()
            alert.show()
            return heIsSure
        }

        fun bind(item: Diary) {
            binding.tvTitle.text = item.title
            binding.tvTextBody.text = item.text
            if (item.alarm == null) binding.ivAlarm.setImageResource(0)

            binding.root.setOnClickListener {
                deleteDiary(item)
                onClick(item.title, item.text)
            }
            binding.root.setOnLongClickListener {
                alertDialog(item)
                true
            }

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