package java.android.cinema.utils

import android.os.CountDownTimer
import android.widget.ProgressBar

class CountDownTimerProgressBar(val progressBar: ProgressBar) {
    private val timer = object :CountDownTimer(10000,200){
        override fun onTick(p0: Long) {
            if(progressBar.progress < progressBar.max){
                progressBar.progress += 1
            }
        }

        override fun onFinish() {
            progressBar.progress = progressBar.max
        }
    }

    fun start(){
        progressBar.progress = 0
        timer.start()
    }

    fun finish(){
        timer.onFinish()
    }

}