package java.android.cinema.utils

import android.os.Handler
import android.os.Looper

class InfiniteThread: Thread() {
    var handler: Handler? = null

    override fun run(){
        Looper.prepare()
        handler = Handler(Looper.myLooper()!!)
        Looper.loop()
    }
}