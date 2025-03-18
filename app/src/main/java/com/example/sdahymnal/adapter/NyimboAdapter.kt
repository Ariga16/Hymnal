package com.dacasa.nyimbozakristo.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.nyimbozak.pojos.Wimbo

import com.example.sdahymnal.R
import com.example.sdahymnal.SongActivity

class NyimboAdapter(
    private val context: Context,
    titles: Array<String>,
    contents: Array<String>
) : RecyclerView.Adapter<NyimboAdapter.ViewHolder>(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var mData: MutableList<Wimbo> = mutableListOf()
    private var mDataFiltered: MutableList<Wimbo> = mutableListOf()

    init {
        if (titles.size == contents.size) {
            for (i in titles.indices) {
                val wimboMpya = Wimbo(titles[i], contents[i])
                mData.add(wimboMpya)
                mDataFiltered.add(wimboMpya)
            }
        } else {
            Toast.makeText(context, "Number of titles and contents do not match", Toast.LENGTH_LONG).show()
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val key = charSequence.toString()
                mDataFiltered = if (key.isEmpty()) {
                    mData.toMutableList()
                } else {
                    mData.filter { it.title.contains(key, ignoreCase = true) }.toMutableList()
                }
                return FilterResults().apply { values = mDataFiltered }
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                mDataFiltered = filterResults?.values as MutableList<Wimbo>
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.custom_layout_forrecyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentSong = mDataFiltered[position]
        holder.songTitle.text = currentSong.title
        holder.songContent.text = currentSong.content
    }

    override fun getItemCount(): Int {
        return mDataFiltered.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val songTitle: TextView = itemView.findViewById(R.id.tvsongTitle)
        val songContent: TextView = itemView.findViewById(R.id.tvSongContent)
        val llContainer: LinearLayout? = null
        val cvContainer: CardView? = null

        init {
            itemView.setOnClickListener {
                val intent = Intent(it.context, SongActivity::class.java)
                val wimbo = mDataFiltered[adapterPosition]
                intent.putExtra("title of song", wimbo.title)
                intent.putExtra("content of song", wimbo.content)
                it.context.startActivity(intent)
            }
        }
    }
}
