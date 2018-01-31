package com.example.nglauber.archcompsample

import android.databinding.BaseObservable
import android.databinding.Bindable

class Score: BaseObservable() {
    var teamA: Int = 0
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.teamA)
        }
    var teamB: Int = 0
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.teamB)
        }
}