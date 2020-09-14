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
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class SyncFragment: Fragment(R.layout.fragment_sync) {

    @Inject
    lateinit var model: SyncFragmentModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as App)
            .component
            .build()
            .inject(this)

        model.subscribeLoadingState().observe(viewLifecycleOwner,{loadingState ->
            if (loadingState == LoadingState.LOADING) {
                progressBar.visibility = View.VISIBLE
                buttonGet.isEnabled = false
                buttonSet.isEnabled = false
            }

            if (loadingState == LoadingState.DONE) {
                setToast(getString(R.string.done_message))

                progressBar.visibility = View.INVISIBLE
                buttonGet.isEnabled = true
                buttonSet.isEnabled = true
            }

            if (loadingState == LoadingState.SERVER_ERROR) {
                setToast(getString(R.string.something_went_wrong))

                progressBar.visibility = View.INVISIBLE
                buttonGet.isEnabled = true
                buttonSet.isEnabled = true
            }

            if(loadingState == LoadingState.CONNECTION_ERROR){
                setToast(getString(R.string.there_is_no_internet_connection))

                progressBar.visibility = View.INVISIBLE
                buttonGet.isEnabled = true
                buttonSet.isEnabled = true
            }
        })

        buttonGet.setOnClickListener {
            if(isOnline())
                model.getAllData()
            else{
                model.setLoadingState(LoadingState.LOADING)
                tryGetAllData(0)
            }
        }

        buttonSet.setOnClickListener {
            if(isOnline())
                model.setAllData()
            else{
                model.setLoadingState(LoadingState.LOADING)
                trySetAllData(0)
            }
        }
    }

    private fun trySetAllData(iteration: Int){
        System.err.println(iteration)

        if(iteration == 11){
            model.setLoadingState(LoadingState.CONNECTION_ERROR)
            return
        }

        Timer().schedule(4000) {
            if(isOnline())
                model.setAllData()
            else{
                trySetAllData(iteration.plus(1))
            }
        }
    }

    private fun tryGetAllData(iteration: Int){
        System.err.println(iteration)

        if(iteration == 11){
            model.setLoadingState(LoadingState.CONNECTION_ERROR)
            return
        }

        //todo плохой способ
        Timer().schedule(4000) {
            if(isOnline())
                model.getAllData()
            else{
                tryGetAllData(iteration.plus(1))
            }
        }
    }

    private fun setToast(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun isOnline(): Boolean {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        return activeNetwork != null
    }
}