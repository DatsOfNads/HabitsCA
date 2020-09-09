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
import com.example.habitsca.R
import kotlinx.android.synthetic.main.fragment_add_and_edit.*
import java.text.SimpleDateFormat
import java.util.*

abstract class AddAndEditFragment: Fragment(R.layout.fragment_add_and_edit), DatePickerDialog.OnDateSetListener {

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    companion object{
        const val EMPTY_STRING = ""
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
            datePickerDialog.show()
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

    open fun createToastError() = Toast.makeText(requireContext(), "Заполнены не все поля!", Toast.LENGTH_SHORT).show()

    abstract fun onTimeChosen(chosenTime: Long)
}