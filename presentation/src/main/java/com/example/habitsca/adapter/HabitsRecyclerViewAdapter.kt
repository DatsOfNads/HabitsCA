package com.example.habitsca.adapter

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
        holder.bind(habits[position])

        holder.constraintLayout.setOnClickListener {
            editHabitListener(habits[position])
        }

        holder.textViewAdd.setOnClickListener {
            addDoneDateListener(habits[position])
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

            chipDoneDates.text = "Выполнено ${habit.doneDates?.size?.minus(1)} раз"

            chipFrequency.text = habit.count.toString().plus(
                when(habit.frequency){
                    Frequency.A_DAY -> " раз в день"
                    Frequency.A_WEEK -> " раз в неделю"
                    Frequency.A_MONTH -> " раз в месяц"
                    Frequency.A_YEAR -> " раз в год"
                    else -> null
                }
            )
        }
    }
}