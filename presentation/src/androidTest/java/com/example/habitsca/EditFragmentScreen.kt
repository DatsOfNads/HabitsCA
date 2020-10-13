package com.example.habitsca

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.agoda.kakao.chipgroup.KChipGroup
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.edit.KTextInputLayout
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher

class EditFragmentScreen: Screen<EditFragmentScreen>() {

    val floatingActionButton = KButton{
        withId(R.id.floatingActionButton)
    }

    val textInputLayoutTitle = KTextInputLayout{
        withId(R.id.textInputLayoutTitle)
    }

    val textInputEditTextTitle = KEditText{
        withId(R.id.textInputEditTextTitle)
    }

    val textInputLayoutDescription = KTextInputLayout{
        withId(R.id.textInputLayoutDescription)
    }

    val textInputLayoutDate = KTextInputLayout{
        withId(R.id.textInputLayoutDate)
    }

    val textInputLayoutPriority = KTextInputLayout{
        withId(R.id.textInputLayoutPriority)
    }

    val textInputLayoutCount = KTextInputLayout{
        withId(R.id.textInputLayoutCount)
    }

    val textInputLayoutFrequency = KTextInputLayout{
        withId(R.id.textInputLayoutFrequency)
    }

    val radioButtonGoodHabit = KButton{
        withId(R.id.radioButtonGoodHabit)
    }

    val radioButtonBadHabit = KButton{
        withId(R.id.radioButtonBadHabit)
    }

    val actionDelete = KButton{
        withId(R.id.action_delete)
    }

    class Item(parent : Matcher<View>) : KRecyclerItem<Item>(parent){
        val title = KTextView(parent) {withId(R.id.title)}
        val description = KTextView(parent) {withId(R.id.description)}
        val chipGroup = KChipGroup(parent){withId(R.id.chipGroup)}
    }

    val recycler = KRecyclerView( {
        withId(R.id.recycler)
    },{
        itemType(::Item)
    })
}