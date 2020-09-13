package com.example.habitsca.view.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.example.domain.model.Habit
import com.example.domain.model.`object`.Frequency
import com.example.domain.model.`object`.Priority
import com.example.domain.model.`object`.Type
import com.example.habitsca.App
import com.example.habitsca.R
import com.example.habitsca.view.viewmodel.AddFragmentModel
import kotlinx.android.synthetic.main.fragment_add_and_edit.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddFragment: AddAndEditFragment() {

    @Inject
    lateinit var model: AddFragmentModel

    private var chosenTime: Long = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as App)
            .component
            .build()
            .inject(this)

        floatingActionButton.setOnClickListener {

            val habit = createNewHabit() ?: return@setOnClickListener

            model.addHabit(habit)
            Navigation.findNavController(requireView()).popBackStack()
        }

        initView()
    }

    private fun initView(){
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        val currentTime = Calendar.getInstance()
        val timeString = dateFormat.format(currentTime.time)

        chosenTime = currentTime.timeInMillis

        textInputEditTextDate.setText(timeString)
    }

    private fun createNewHabit(): Habit? {

        val priority = when (textInputEditTextPriority.text.toString()){
            resources.getString(R.string.priority_low) -> Priority.LOW
            resources.getString(R.string.priority_medium) -> Priority.MEDIUM
            resources.getString(R.string.priority_high) -> Priority.HIGH
            else -> null
        }

        val frequency = when (textInputEditTextFrequency.text.toString()){
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
        val count = textInputEditTextCount.text.toString()

        var hasErrors = false

        if(title.isEmpty()){
            textInputLayoutTitle.error = getString(R.string.enter_the_name_of_the_habit)
            hasErrors = true
        }

        if(description.isEmpty()){
            textInputLayoutDescription.error = getString(R.string.enter_a_description)
            hasErrors = true
        }

        if(count.isEmpty()){
            textInputLayoutCount.error = getString(R.string.enter_the_count)
            hasErrors = true
        }

        if (priority == null){
            textInputLayoutPriority.error = getString(R.string.select_an_option_from_the_list)
            hasErrors = true
        }

        if (frequency == null){
            textInputLayoutFrequency.error = getString(R.string.select_an_option_from_the_list)
            hasErrors = true
        }

        if(hasErrors){
            createToastError()
            return null
        }

        return Habit(
            uid = UUID.randomUUID().toString(),
            title = title,
            description = description,
            priority = priority!!,
            type = type,
            count = count.toInt(),
            frequency = frequency!!,
            date = chosenTime,
            doneDates = emptyList()
        )
    }

    override fun onTimeChosen(chosenTime: Long) {
        this.chosenTime = chosenTime
    }
}