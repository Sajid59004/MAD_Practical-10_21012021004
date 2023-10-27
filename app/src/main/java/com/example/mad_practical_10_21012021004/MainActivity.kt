package com.example.mad_practical_10_21012021004

import android.app.Person
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val floatingBt=findViewById<FloatingActionButton>(R.id.floatingBt)

        floatingBt.setOnClickListener {
            sendDataToListView()
            Intent(this,MapsActivity::class.java).apply { startActivity(this) }
        }

    }
    private fun getPersonDetailsFromJson(sJson: String?) {
        val personList = ArrayList<Contect>()
        try {
            val jsonArray = JSONArray(sJson)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray[i] as JSONObject
                val person = Contect(jsonObject)
                personList.add(person)
            }

            listview1.adapter = PersonAdapter(this, personList)
        } catch (ee: JSONException) {
            ee.printStackTrace()
        }
    }
    fun sendDataToListView(){
//        val personListView = findViewById<ListView>(R.id.listView1)
//        val personList = arrayListOf(
//            Contect("1", "Sagar" , "abc@gmail.com" , "9898987867" , "JND" , 1.2 , 1.4),
//            Contect("2" , "Palak" , "xyz111@gmail.com" , "4545989769" , "KSD" , 1.5 , 3.12),
//            Contect("3" , "Bhikho" , "xy8753z@gmail.com" , "3490989769" , "KSD" , 1.5 , 3.12),
//            Contect("4" , "Arjan" , "xy4312z@gmail.com" , "1090989769" , "KSD" , 1.5 , 3.12),
//            Contect("5" , "Lalo" , "xyz111@gmail.com" , "5440989769" , "KSD" , 1.5 , 3.12)
//            )
//
//        personListView.adapter=ContectAdepter(this , personList)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = HttpRequest().makeServiceCall(
                    "https://api.json-generator.com/templates/qjeKFdjkXCdK/data",
                    "rbn0rerl1k0d3mcwgw7dva2xuwk780z1hxvyvrb1")
                withContext(Dispatchers.Main) {
                    try {
                        if(data != null)
                            runOnUiThread{getPersonDetailsFromJson(data)}
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}