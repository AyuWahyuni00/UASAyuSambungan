package com.informatika.databarang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.informatika.databarang.adapter.ListContent
import com.informatika.databarang.model.ResponseBarang
import com.informatika.databarang.network.koneksi
import com.informatika.databarang.service.SessionPreferences
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var sessionPreferences: SessionPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sessionPreferences =    SessionPreferences(this)
        cekSession()
        tv_username.text = sessionPreferences.getUserName()
//        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val i = Intent(this, InsertDataActivity::class.java)
            startActivity(i)
        }
        getData()

    }
    fun cekSession(){
        sessionPreferences = SessionPreferences(this)
        val userName = sessionPreferences.getUserName()
        if (userName == null){
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
    public fun getData() {
        koneksi.service.getBarang().enqueue(object : Callback<ResponseBarang> {
            override fun onFailure(call: Call<ResponseBarang>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseBarang>,
                response: Response<ResponseBarang>
            ) {
                if (response.isSuccessful) {
                    val dataBody = response.body()
                    val datacontent = dataBody!!.data

                    val rvAdapter = ListContent(datacontent, this@MainActivity, "MainActivity")
                    rvAdapter.notifyDataSetChanged()
                    Log.d("bpesan", datacontent.toString())
                    rv_data_barang.apply {
                        adapter = rvAdapter
                        layoutManager = LinearLayoutManager(this@MainActivity)
                    }
                }else{
                    Log.d("bpesan", "error")
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}