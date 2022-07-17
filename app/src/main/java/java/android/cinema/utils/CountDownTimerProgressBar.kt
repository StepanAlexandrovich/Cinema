package java.android.cinema.utils

import android.os.CountDownTimer

class CountDownTimerProgressBar {

    // подключить с колбэком
    fun downLoad(){
        //val progressBar = binding.progressBar

        object : CountDownTimer(10000,500){
            override fun onTick(p0: Long) {
                //if(progressBar.progress < progressBar.max){
                    //progressBar.progress += 10
                //}
            }

            override fun onFinish() { }

        }.start()
    }
}