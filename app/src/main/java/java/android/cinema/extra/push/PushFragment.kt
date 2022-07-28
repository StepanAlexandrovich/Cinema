package java.android.cinema.extra.push

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.android.cinema.activity.MainActivity
import java.android.cinema.databinding.FragmentPushBinding
import java.android.cinema.utils.PrintVisible

class PushFragment: Fragment(),View.OnClickListener{

    private var _binding: FragmentPushBinding? = null
    val binding: FragmentPushBinding get() { return _binding!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPushBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonToken.setOnClickListener(this)
        binding.buttonPush.setOnClickListener(this)

        binding.buttonToken.setOnClickListener(View.OnClickListener {

            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if(!task.isSuccessful){
                    PrintVisible.printShort("неуд")
                    return@OnCompleteListener
                }

                val token = task.result
                binding.editTextText.setText(token)
            })
        })

        requireActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when (p0?.getId()) {
            binding.buttonToken.id   -> { requestToken()  }
            binding.buttonPush.id    -> { pushNotification("test","test") }
        }
    }

    private fun requestToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if(!task.isSuccessful){
                PrintVisible.printShort("неуд")
                return@OnCompleteListener
            }

            val token = task.result
            binding.editTextText.setText(token)
        })
    }

    val CHANNEL_HIGH_ID = "channel_111"
    val CHANNEL_LOW_ID  = "channel_222"
    val NOTIFICATION_ID = 1

    private fun pushNotification(title:String,body:String){
        (requireActivity() as MainActivity).pushNotification(title,body)
    }

}


