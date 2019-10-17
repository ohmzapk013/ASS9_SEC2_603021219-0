package com.example.lab9mysqlupdatedelete

/*import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_edit_students.*
import javax.security.auth.callback.Callback*/

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_edit_students.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class EditStudentsActivity : AppCompatActivity() {

    val createClient = StudentAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_students)

        val mId = intent.getStringExtra("mId")
        val mName = intent.getStringExtra("mName")
        val mAge = intent.getStringExtra("mAge")

        edit_id.setText(mId)
        edit_id.isEnabled = false
        edit_name.setText(mName)
        edit_age.setText(mAge)

    }

    fun saveStudent(v: View){
        createClient.updateStudent(
            edit_id.text.toString(),
            edit_name.text.toString(),
            edit_age.text.toString().toInt()).enqueue(object : Callback<Student>{
            override fun onFailure(call: Call<Student>, t: Throwable) {
                Toast.makeText(applicationContext,"Error", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<Student>, response: Response<Student>) {
                if(response.isSuccessful()){
                    Toast.makeText(applicationContext, "Successfully Inserted", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else{
                    Toast.makeText(applicationContext,"Error", Toast.LENGTH_SHORT).show()
                }

            }

        })



    }

    fun deleteStudent(v: View){
        val builder = AlertDialog.Builder(this)
        val possitiveButtonClick = { dialog: DialogInterface, which: Int ->
            createClient.deleteStudent(edit_id.text.toString()).enqueue(object : Callback<Student>{
                override fun onFailure(call: Call<Student>, t: Throwable) {

                }

                override fun onResponse(call: Call<Student>, response: Response<Student>) {
                     if(response.isSuccessful()){
                         Toast.makeText(applicationContext,"Deleted",Toast.LENGTH_SHORT).show()
                     }
                }

            })
            finish()


        }

        val negative = { dialog: DialogInterface, which :Int-> dialog.cancel()}
        builder.setTitle("Warning")
        builder.setMessage("Do you want to delete this student ?")
        builder.setPositiveButton("Yes", possitiveButtonClick)
        builder.setNegativeButton("No", negative)
        builder.show()
    }

}
