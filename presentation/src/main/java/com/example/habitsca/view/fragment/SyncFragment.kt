package com.example.habitsca.view.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.domain.model.LoadingState
import com.example.habitsca.App
import com.example.habitsca.R
import com.example.habitsca.view.viewmodel.SyncFragmentModel
import kotlinx.android.synthetic.main.fragment_sync.*
import javax.inject.Inject

class SyncFragment: Fragment(R.layout.fragment_sync) {

    @Inject
    lateinit var model: SyncFragmentModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as App)
            .component
            .build()
            .inject(this)

        model.subscribeLoadingState().observe(viewLifecycleOwner,{
            if (it == LoadingState.LOADING) {
                progressBar.visibility = View.VISIBLE
                buttonGet.isEnabled = false
                buttonSet.isEnabled = false
            }

            if (it == LoadingState.DONE) {
                Toast.makeText(context, "Готово!", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
                buttonGet.isEnabled = true
                buttonSet.isEnabled = true
            }

            if (it == LoadingState.ERROR) {
                Toast.makeText(context, "Что-то пошло не так(", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
                buttonGet.isEnabled = true
                buttonSet.isEnabled = true
            }
        })

        buttonGet.setOnClickListener {
            if(isOnline())
                model.getAllData()
            else
                Toast.makeText(context, "Отсутствует интернет-подключение", Toast.LENGTH_SHORT).show()
        }

        buttonSet.setOnClickListener {
            if(isOnline())
                model.setAllData()
            else
                Toast.makeText(context, "Отсутствует интернет-подключение", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isOnline(): Boolean {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        return activeNetwork != null
    }
}