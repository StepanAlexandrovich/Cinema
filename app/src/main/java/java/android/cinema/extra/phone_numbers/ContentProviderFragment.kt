package java.android.cinema.extra.phone_numbers

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.android.cinema.databinding.FragmentContentProviderBinding

class ContentProviderFragment: Fragment(){

    private var _binding: FragmentContentProviderBinding? = null
    val binding: FragmentContentProviderBinding get() { return _binding!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentProviderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    private fun checkPermission(){
        val permResult = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
        PackageManager.PERMISSION_GRANTED
        if(permResult == PackageManager.PERMISSION_GRANTED){
            getContacts()
        }else{
            permissionRequest(Manifest.permission.READ_CONTACTS)
        }
    }

    private fun permissionRequest(permission: String){
        requestPermissions(arrayOf(permission), REQUEST_CODE_READ_CONTACTS)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_CODE_READ_CONTACTS){

            for(pIndex in permissions.indices){
                if(permissions[pIndex] == Manifest.permission.READ_CONTACTS && grantResults[pIndex] == PackageManager.PERMISSION_GRANTED){
                    getContacts()
                }
            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private val REQUEST_CODE_READ_CONTACTS = 999

    private fun getContacts(){
        val contentResolver: ContentResolver = requireContext().contentResolver

        val cursorWithContacts: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )

        cursorWithContacts?.let { cursor ->
            for(i in 0..cursorWithContacts.count - 1){
                cursor.moveToPosition(i)
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                val contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val number = getNumberFromID(contentResolver,contactId)

                binding.containerForContacts.addView(TextView(requireContext()).apply {
                    text = "${name} - > ${number}"
                    textSize = 25f
                })
            }
        }

        cursorWithContacts?.close()
    }

    private fun getNumberFromID(cr: ContentResolver, contactId: String) :String {
        val phones = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null
        )
        var number: String = "none"
        phones?.let { cursor ->
            while (cursor.moveToNext()) {
                number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            }
        }
        return number
    }





}


