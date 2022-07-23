package java.android.cinema.utils

import android.os.CountDownTimer
import android.widget.ProgressBar

object CountDownTimerProgressBar {

    fun downLoad(progressBar: ProgressBar){

        object : CountDownTimer(10000,500){
            override fun onTick(p0: Long) {
                if(progressBar.progress < progressBar.max){
                    progressBar.progress += 10
                }
            }

            override fun onFinish() { }

        }.start()
    }

}