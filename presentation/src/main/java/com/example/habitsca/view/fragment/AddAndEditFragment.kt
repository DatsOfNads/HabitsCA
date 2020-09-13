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
import com.example.domain.model.`object`.Priority
import com.example.habitsca.R
import kotlinx.android.synthetic.main.fragment_add_and_edit.*
import java.text.SimpleDateFormat
import java.util.*

abstract class AddAndEditFragment: Fragment(R.layout.fragment_add_and_edit), DatePickerDialog.OnDateSetListener {

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    companion object{
        const val EMPTY_STRING = ""

        var priority: Int = Priority.LOW
        var frequency: Int = Frequency.A_DAY
    }

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

            datePickerDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
            datePickerDialog.show()
        }

        textInputEditTextDate.keyListener = null

        val adapterFrequency = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayOf(
                resources.getString(R.string.period_a_day),
                resources.getString(R.string.period_a_week),
                resources.getString(R.string.period_a_month),
                resources.getString(R.string.period_a_year)
            )
        )

        textInputEditTextFrequency.apply {
            setAdapter(adapterFrequency)
            keyListener = null

            setOnItemClickListener { adapterView, _, i, _ ->
                when (adapterView.getItemAtPosition(i) as String) {
                    resources.getString(R.string.period_a_day) -> frequency = Frequency.A_DAY
                    resources.getString(R.string.period_a_week) -> frequency = Frequency.A_WEEK
                    resources.getString(R.string.period_a_month) -> frequency = Frequency.A_MONTH
                    resources.getString(R.string.period_a_year) -> frequency = Frequency.A_YEAR
                }
            }

            when(frequency){
                Frequency.A_DAY -> setText( resources.getString(R.string.period_a_day), false)
                Frequency.A_WEEK -> setText( resources.getString(R.string.period_a_week), false)
                Frequency.A_MONTH -> setText( resources.getString(R.string.period_a_month), false)
                Frequency.A_YEAR -> setText( resources.getString(R.string.period_a_year), false)
            }
        }

        val adapterPriority = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayOf(
                resources.getString(R.string.priority_low),
                resources.getString(R.string.priority_medium),
                resources.getString(R.string.priority_high)
            )
        )

        textInputEditTextPriority.apply {
            setAdapter(adapterPriority)
            keyListener = null

            setOnItemClickListener { adapterView, _, i, _ ->
                when (adapterView.getItemAtPosition(i) as String) {
                    resources.getString(R.string.priority_low) -> priority = Priority.LOW
                    resources.getString(R.string.priority_medium) -> priority = Priority.MEDIUM
                    resources.getString(R.string.priority_high) -> priority = Priority.HIGH
                }
            }

            when(priority){
                Priority.LOW -> setText( resources.getString(R.string.priority_low), false)
                Priority.MEDIUM -> setText( resources.getString(R.string.priority_medium), false)
                Priority.HIGH -> setText( resources.getString(R.string.priority_high), false)
            }
        }

        watchErrors()
    }

    private fun watchErrors() {
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

        textInputEditTextCount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.isNotEmpty() == true) {
                    textInputLayoutCount.error =
                        EMPTY_STRING
                }
            }

        })

        textInputEditTextFrequency.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.isNotEmpty() == true) {
                    textInputLayoutFrequency.error =
                        EMPTY_STRING
                }
            }

        })
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        calendar.setTimeToStart()

        val millis = calendar.timeInMillis

        val timeString = dateFormat.format(millis)
        textInputEditTextDate.setText(timeString)

        onTimeChosen(millis)
    }

    open fun Calendar.setTimeToStart(){
        this.set(Calendar.MILLISECOND, 0)
        this.set(Calendar.SECOND, 0)
        this.set(Calendar.MINUTE, 0)
        this.set(Calendar.HOUR_OF_DAY, 0)
    }

    open fun createToastError() = Toast.makeText(requireContext(), getString(R.string.not_all_fields_are_filled), Toast.LENGTH_SHORT).show()

    abstract fun onTimeChosen(chosenTime: Long)
}