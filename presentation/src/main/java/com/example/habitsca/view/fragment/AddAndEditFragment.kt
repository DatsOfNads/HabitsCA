package com.example.habitsca.view.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.domain.model.`object`.Frequency
import com.example.domain.model.Habit
import com.example.domain.model.`object`.Priority
import com.example.domain.model.`object`.Type
import com.example.habitsca.R
import kotlinx.android.synthetic.main.fragment_add_and_edit.*
import java.text.SimpleDateFormat
import java.util.*

open class AddAndEditFragment: Fragment(R.layout.fragment_add_and_edit), DatePickerDialog.OnDateSetListener {

    companion object{
        const val EMPTY_STRING = ""
    }

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private var chosenTime = Calendar.getInstance().time

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textInputEditTextDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        val timeString = dateFormat.format(chosenTime)
        textInputEditTextDate.setText(timeString)

        val adapterPriority = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayOf(
                resources.getString(R.string.priority_low),
                resources.getString(R.string.priority_medium),
                resources.getString(R.string.priority_high)
            )
        )

        val adapterPeriod = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayOf(
                resources.getString(R.string.period_a_day),
                resources.getString(R.string.period_a_week),
                resources.getString(R.string.period_a_month),
                resources.getString(R.string.period_a_year)
            )
        )

        textInputEditTextPriority.setAdapter(adapterPriority)
        textInputEditTextPeriod.setAdapter(adapterPeriod)

        checkErrors()
    }

    private fun checkErrors() {
        textInputEditTextTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.isNotEmpty() == true) {
                    textInputLayoutTitle.error =
                        EMPTY_STRING
                }
            }

        })

        textInputEditTextDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.isNotEmpty() == true) {
                    textInputLayoutDescription.error =
                        EMPTY_STRING
                }
            }

        })

        textInputEditTextPriority.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.isNotEmpty() == true) {
                    textInputLayoutPriority.error =
                        EMPTY_STRING
                }
            }

        })

        textInputEditTextNumberOfTimes.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.isNotEmpty() == true) {
                    textInputLayoutNumberOfTimes.error =
                        EMPTY_STRING
                }
            }

        })

        textInputEditTextPeriod.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.isNotEmpty() == true) {
                    textInputLayoutPeriod.error =
                        EMPTY_STRING
                }
            }

        })
    }

    open fun setChosenTime(chosenTime: Long){
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = chosenTime
        this.chosenTime = calendar.time
    }

    open fun createNewHabit(): Habit? {

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
            uid  = UUID.randomUUID().toString(),
            title = title,
            description = description,
            priority = priority!!,
            type = type,
            count = count.toInt(),
            frequency = frequency!!,
            date = chosenTime.time,
            doneDates = listOf()
        )
    }

    private fun createToastError() =  Toast.makeText(requireContext(), "Заполнены не все поля!", Toast.LENGTH_SHORT).show()

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {

        val calendar = Calendar.getInstance()

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        val millis = calendar.timeInMillis

        val timeString = dateFormat.format(millis)
        textInputEditTextDate.setText(timeString)

        chosenTime = calendar.time
    }
}