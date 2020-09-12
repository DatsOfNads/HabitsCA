package com.example.habitsca.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Habit
import com.example.domain.model.`object`.Frequency
import com.example.domain.model.`object`.Priority
import com.example.domain.model.`object`.Type
import com.example.habitsca.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_habit.*
import java.util.*
import kotlin.collections.ArrayList

class HabitsRecyclerViewAdapter(
    private val context: Context,
    private var habits: ArrayList<Habit>,
    val editHabitListener : (Habit) -> Unit,
    val addDoneDateListener: (Habit) -> Unit
): RecyclerView.Adapter<HabitsRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(
            inflater.inflate(
                R.layout.item_habit,
                parent,
                false
            ), context
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val habit = habits[position]

        holder.bind(habit)


        val doneDatesCount = habit.doneDates?.size ?: 0

        if(doneDatesCount != 0){
            val doneDatesPlural = context.resources.getQuantityString(
                R.plurals.plurals_done_dates,
                doneDatesCount,
                doneDatesCount
            )

            holder.chipDoneDates.text = doneDatesPlural
        } else{
            holder.chipDoneDates.text = context.getString(R.string.have_not_done)
        }

        val count = habit.count
        val countPlural = context.resources.getQuantityString(R.plurals.plurals_count, count, count)

        holder.chipFrequency.text = countPlural.plus(
            when(habit.frequency){
                Frequency.A_DAY -> " " + context.getString(R.string.period_a_day)
                    .toLowerCase(Locale.ROOT)
                Frequency.A_WEEK -> " " + context.getString(R.string.period_a_week)
                    .toLowerCase(Locale.ROOT)
                Frequency.A_MONTH -> " " + context.getString(R.string.period_a_month)
                    .toLowerCase(Locale.ROOT)
                Frequency.A_YEAR -> " " + context.getString(R.string.period_a_year)
                    .toLowerCase(Locale.ROOT)
                else -> null
            }
        )

        holder.constraintLayout.setOnClickListener {
            editHabitListener(habit)
        }

        holder.textViewAdd.setOnClickListener {
            addDoneDateListener(habit)
        }
    }

    override fun getItemCount(): Int = habits.size

    fun addAll(habits: ArrayList<Habit>){

        this.habits = habits
        notifyDataSetChanged()
    }

    class MyViewHolder(
        override val containerView: View,
        val context: Context
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(habit: Habit) {

            title.text = habit.title
            description.text = habit.description

            chipType.text = when(habit.type){
                Type.GOOD -> context.getString(R.string.good_habit)
                Type.BAD -> context.getString(R.string.bad_habit)
                else -> null
            }

            chipPriority.text = when(habit.priority){
                Priority.LOW -> context.getString(R.string.priority_low_full)
                Priority.MEDIUM -> context.getString(R.string.priority_medium_full)
                Priority.HIGH -> context.getString(R.string.priority_high_full)
                else -> null
            }
        }
    }
}