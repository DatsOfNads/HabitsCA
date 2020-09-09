package com.example.habitsca.view.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.domain.model.Habit
import com.example.domain.model.`object`.Frequency
import com.example.domain.model.`object`.Priority
import com.example.domain.model.`object`.Type
import com.example.habitsca.App
import com.example.habitsca.R
import com.example.habitsca.view.viewmodel.EditFragmentModel
import kotlinx.android.synthetic.main.fragment_add_and_edit.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class EditFragment: AddAndEditFragment() {

    private val args: EditFragmentArgs? by navArgs()

    private lateinit var habitForEditing: Habit

    @Inject
    lateinit var model: EditFragmentModel

    private var chosenTime: Long = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        (activity?.application as App)
            .component
            .build()
            .inject(this)

        habitForEditing = args?.habit!!

        chosenTime = habitForEditing.date
        initView(habitForEditing)

        floatingActionButton.setOnClickListener {

            val habit = createNewHabit() ?: return@setOnClickListener
            habit.uid = habitForEditing.uid
            habit.doneDates = habitForEditing.doneDates

            model.editHabit(habit)

            Navigation.findNavController(requireView()).popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.edit_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_delete) {
            showAlertDialog()
        }

        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        )
                || super.onOptionsItemSelected(item)
    }

    private fun initView(habit: Habit) {
        textInputEditTextTitle.setText(habit.title)

        textInputEditTextDescription.setText(habit.description)

        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val timeString = dateFormat.format(habit.date)

        textInputEditTextDate.setText(timeString)

        textInputEditTextPriority.setText(
            when (habit.priority) {
                Priority.LOW -> resources.getString(R.string.priority_low)
                Priority.MEDIUM -> resources.getString(R.string.priority_medium)
                Priority.HIGH -> resources.getString(R.string.priority_high)
                else -> null
            }
        )

        textInputEditTextNumberOfTimes.setText(habit.count.toString())

        textInputEditTextPeriod.setText(
            when (habit.frequency) {
                Frequency.A_DAY -> resources.getString(R.string.period_a_day)
                Frequency.A_WEEK -> resources.getString(R.string.period_a_week)
                Frequency.A_MONTH -> resources.getString(R.string.period_a_month)
                Frequency.A_YEAR -> resources.getString(R.string.period_a_year)
                else -> null
            }
        )

        when (habit.type) {
            Type.GOOD -> radioButtonGoodHabit.isChecked = true
            Type.BAD -> radioButtonBadHabit.isChecked = true
        }
    }

    private fun createNewHabit(): Habit? {

        val priority = when (textInputEditTextPriority.text.toString()){
            resources.getString(R.string.priority_low) -> Priority.LOW
            resources.getString(R.string.priority_medium) -> Priority.MEDIUM
            resources.getString(R.string.priority_high) -> Priority.HIGH
            else -> null
        }

        val frequency = when (textInputEditTextPeriod.text.toString()){
            resources.getString(R.string.period_a_day) -> Frequency.A_DAY
            resources.getString(R.string.period_a_week) -> Frequency.A_WEEK
            resources.getString(R.string.period_a_month) -> Frequency.A_MONTH
            resources.getString(R.string.period_a_year) -> Frequency.A_YEAR
            else -> null
        }

        var type = Type.GOOD

        if(radioButtonBadHabit.isChecked)
            type = Type.BAD

        val title = textInputEditTextTitle.text.toString()
        val description = textInputEditTextDescription.text.toString()
        val count = textInputEditTextNumberOfTimes.text.toString()

        var hasErrors = false

        if(title.isEmpty()){
            textInputLayoutTitle.error = "Введите название привычки"
            hasErrors = true
        }

        if(description.isEmpty()){
            textInputLayoutDescription.error = "Введите описание"
            hasErrors = true
        }

        if(count.isEmpty()){
            textInputLayoutNumberOfTimes.error = "Введите количество раз"
            hasErrors = true
        }

        if (priority == null){
            textInputLayoutPriority.error = "Выберите вариант из списка"
            hasErrors = true
        }

        if (frequency == null){
            textInputLayoutPeriod.error = "Выберите вариант из списка"
            hasErrors = true
        }

        if(hasErrors){
            createToastError()
            return null
        }

        return Habit(
            uid = habitForEditing.uid,
            title = title,
            description = description,
            priority = priority!!,
            type = type,
            count = count.toInt(),
            frequency = frequency!!,
            date = chosenTime,
            doneDates = habitForEditing.doneDates
        )
    }

    private fun showAlertDialog(){
        val builder  = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Удалить привычку?")
        builder.setMessage("Это действие нельзя будет отменить")
        builder.setPositiveButton("Удалить") { dialog, id ->
            model.deleteHabit(habitForEditing)
            Navigation.findNavController(requireView()).popBackStack()
        }

        builder.setNegativeButton("Отмена"){ dialog, id ->
            dialog.cancel()
        }
        builder.show()
    }

    override fun onTimeChosen(chosenTime: Long) {
        this.chosenTime = chosenTime
    }
}