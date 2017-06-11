package br.com.nglauber.animationdemo.transition.scene

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.*
import android.view.Gravity
import android.view.View
import br.com.nglauber.animationdemo.R
import kotlinx.android.synthetic.main.activity_transition_form.*

class TransitionFormActivity : AppCompatActivity() {

    var fieldsVisible: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_form)
        btnSave.setOnClickListener { onSaveClick() }
    }

    fun onSaveClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && chkAnimate.isChecked) {
            if (chkUseXml.isChecked) {
                val transition = TransitionInflater.from(this)
                        .inflateTransition(if (fieldsVisible) R.transition.transition_form_hide else R.transition.transition_form_show)
                TransitionManager.beginDelayedTransition(constraintlayout, transition)

            } else {
                val transitionSet = TransitionSet()
                transitionSet.ordering = TransitionSet.ORDERING_SEQUENTIAL
                if (fieldsVisible) {
                    transitionSet.addTransition(Slide(Gravity.RIGHT))
                    transitionSet.addTransition(ChangeBounds())
                } else {
                    transitionSet.addTransition(ChangeBounds())
                    transitionSet.addTransition(Slide(Gravity.RIGHT))
                }

                TransitionManager.beginDelayedTransition(constraintlayout, transitionSet)
            }
        }
        fieldsVisible = !fieldsVisible
        val visibility = if (fieldsVisible) View.VISIBLE else View.GONE
        txtNome.visibility = visibility
        edtNome.visibility = visibility
        txtIdade.visibility = visibility
        edtIdade.visibility = visibility
    }
}
