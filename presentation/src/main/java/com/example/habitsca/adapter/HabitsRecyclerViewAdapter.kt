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
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val habit = habits[position]

        holder.bind(habit)

        val doneDatesCount = habit.doneDates?.size?.minus(1) ?: 0
        val doneDatesPlural = context.resources.getQuantityString(R.plurals.plurals_done_dates, doneDatesCount, doneDatesCount)

        holder.chipDoneDates.text = doneDatesPlural

        val count = habit.count
        val countPlural = context.resources.getQuantityString(R.plurals.plurals_count, count, count)

        holder.chipFrequency.text = countPlural.plus(
            when(habit.frequency){
                Frequency.A_DAY -> " в день"
                Frequency.A_WEEK -> " в неделю"
                Frequency.A_MONTH -> " в месяц"
                Frequency.A_YEAR -> " в год"
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
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(habit: Habit) {

            title.text = habit.title
            description.text = habit.description

            chipType.text = when(habit.type){
                Type.GOOD -> "Хорошая"
                Type.BAD -> "Плохая"
                else -> null
            }

            chipPriority.text = when(habit.priority){
                Priority.LOW -> "Приоритет низкий"
                Priority.MEDIUM -> "Приоритет средний"
                Priority.HIGH -> "Приоритет высокий"
                else -> null
            }
        }
    }
}