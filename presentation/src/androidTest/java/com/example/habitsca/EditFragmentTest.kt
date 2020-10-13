package com.example.habitsca

import android.widget.DatePicker
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.agoda.kakao.common.utilities.getResourceString
import com.agoda.kakao.edit.TextInputLayoutAssertions
import com.example.habitsca.view.MainActivity
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test


@LargeTest //это надо?
class EditFragmentTest {

    @Rule
    @JvmField
    var rule = ActivityScenarioRule(MainActivity::class.java)

    private val screen = EditFragmentScreen()

    @Test
    fun editFragmentTest(){
        createHabitForTesting()
        openTestedHabit()
        checkTestingHabitIsCorrect("Some title",
            "Some description",
            getResourceString(R.string.priority_medium),
            "3",
            getResourceString(R.string.period_a_day)
        )
        changeHabitData()
        openTestedHabit()
        checkTestingHabitIsCorrect("New title",
            "New description",
            getResourceString(R.string.priority_high),
            "4",
            getResourceString(R.string.period_a_week)
        )

        screen{

            actionDelete.click()

            onView(withText(getResourceString(R.string.action_delete)))
            .inRoot(isDialog())
            .perform(click())
        }
    }

    private fun createHabitForTesting(){
        screen{
            floatingActionButton.click()

            textInputLayoutTitle.edit.typeText("Some title")
            textInputLayoutDescription.edit.typeText("Some description")

            textInputLayoutPriority{
                click()
                onView(withText(getResourceString(R.string.priority_medium)))
                    .inRoot(RootMatchers.isPlatformPopup())
                    .perform(ViewActions.click())
            }

            textInputLayoutCount.edit.typeText("3")

            textInputLayoutFrequency{
                click()
                onView(withText(getResourceString(R.string.period_a_day)))
                    .inRoot(RootMatchers.isPlatformPopup())
                    .perform(ViewActions.click())
            }

            Espresso.closeSoftKeyboard()
            floatingActionButton.click()
        }
    }

    private fun openTestedHabit(){
        screen{
            recycler {
                childAt<EditFragmentScreen.Item>(0) {
                    title{
                        click()
                    }
                }
            }
        }
    }

    private fun checkTestingHabitIsCorrect(
        title: String,
        description: String,
        priority: String,
        count: String,
        frequency: String
    ){
        screen{
            textInputLayoutTitle.edit.hasText(title)
            textInputLayoutDescription.edit.hasText(description)
            textInputLayoutPriority.edit.hasText(priority)
            textInputLayoutCount.edit.hasText(count)
            textInputLayoutFrequency.edit.hasText(frequency)
        }
    }

    private fun changeHabitData(){
        screen{
            //Переписываем название, проверяем корректность ошибок
            textInputLayoutTitle.edit.clearText()
            Espresso.closeSoftKeyboard()
            floatingActionButton.click()

            textInputLayoutTitle.hasError(getResourceString(R.string.enter_the_name_of_the_habit))
            textInputLayoutTitle.edit.typeText("New")
            textInputLayoutTitle.hasNotError()
            textInputLayoutTitle.edit.typeText(" title")

            //Переписываем описание, проверяем корректность ошибок
            textInputLayoutDescription.edit.clearText()
            Espresso.closeSoftKeyboard()
            floatingActionButton.click()

            textInputLayoutDescription.hasError(getResourceString(R.string.enter_a_description))
            textInputLayoutDescription.edit.typeText("New")
            textInputLayoutDescription.hasNotError()
            textInputLayoutDescription.edit.typeText(" description")

            //Проверяем, что можно получить и несколько ошибок сразу
            textInputLayoutDescription.edit.clearText()
            textInputLayoutTitle.edit.clearText()
            Espresso.closeSoftKeyboard()
            floatingActionButton.click()
            textInputLayoutTitle.hasError(getResourceString(R.string.enter_the_name_of_the_habit))
            textInputLayoutDescription.hasError(getResourceString(R.string.enter_a_description))
            textInputLayoutTitle.edit.typeText("New title")
            textInputLayoutDescription.edit.typeText("New description")

            //Меняем дату
            textInputLayoutDate.click()
            onView(
                withClassName(
                    Matchers.equalTo(
                        DatePicker::class.java.name
                    )
                )
            ).perform(PickerActions.setDate(2020, 1, 1))

            onView(withId(android.R.id.button1)).perform(click())
            textInputLayoutDate.edit.hasText("01.01.2020")

            //Меняем приоритет привычки
            textInputLayoutPriority{
                click()
                onView(withText(getResourceString(R.string.priority_high)))
                    .inRoot(RootMatchers.isPlatformPopup())
                    .perform(ViewActions.click())
            }
            textInputLayoutPriority.edit.hasText(getResourceString(R.string.priority_high))

            //Переписываем количество раз, проверяем корректность ошибок
            textInputLayoutCount.edit.clearText()
            Espresso.closeSoftKeyboard()
            floatingActionButton.click()

            textInputLayoutCount.hasError(getResourceString(R.string.enter_the_count))
            textInputLayoutCount.edit.typeText("4")
            textInputLayoutCount.hasNotError()

            //Меняем период
            textInputLayoutFrequency{
                click()
                onView(withText(getResourceString(R.string.period_a_week)))
                    .inRoot(RootMatchers.isPlatformPopup())
                    .perform(ViewActions.click())
            }

            textInputLayoutFrequency.edit.hasText(getResourceString(R.string.period_a_week))

            //Меняем тип привычки
            Espresso.closeSoftKeyboard()
            radioButtonBadHabit.click()
            radioButtonGoodHabit.isNotSelected()

            radioButtonGoodHabit.click()

            //Сохраняяем новую привычку
            floatingActionButton.click()
        }
    }

    private fun TextInputLayoutAssertions.hasNotError() {
        view.check { view, notFoundException ->
            if (view is TextInputLayout) {
                if (view.error != null) {
                    throw AssertionError(
                        "Expected error is null," +
                                " but actual is ${view.error}"
                    )
                }
            } else {
                notFoundException?.let { throw AssertionError(it) }
            }
        }
    }
}