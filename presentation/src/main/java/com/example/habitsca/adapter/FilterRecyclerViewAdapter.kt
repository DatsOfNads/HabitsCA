package com.example.habitsca.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.FilterElement
import com.example.habitsca.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_filter_element.*

class FilterRecyclerViewAdapter(
    private var filterElements: ArrayList<FilterElement>,
    val adapterOnClick : (FilterElement) -> Unit
): RecyclerView.Adapter<FilterRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(
            inflater.inflate(
                R.layout.item_filter_element,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(filterElements[position])
        holder.imageButton.setOnClickListener {
            adapterOnClick(filterElements[position])
        }
    }

    override fun getItemCount()= filterElements.size

    fun addAll(filterElements: ArrayList<FilterElement>){
        this.filterElements = filterElements
        notifyDataSetChanged()
    }

    class MyViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(filterElement: FilterElement){
            when(filterElement.type){
                FilterElement.Type.SEARCH -> textViewTitle.text =  "Поиск:  ${filterElement.title}"
                FilterElement.Type.SORT -> textViewTitle.text = "Сортировка:  ${filterElement.title}"
            }

        }
    }
}