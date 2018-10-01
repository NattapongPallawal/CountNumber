package com.example.natta.countnumber

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var number: Int = 0
    private lateinit var count : Count

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countDown.setOnCheckedChangeListener { _, isChecked ->
            countUp.isChecked = !isChecked
        }

        countUp.setOnCheckedChangeListener { _, isChecked ->
            countDown.isChecked = !isChecked
        }

        editText_num.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textView_count.text = s
            }

        })

        btn_ok.setOnClickListener {
            try {
                number = editText_num.text.toString().toInt()
                count = Count()
                count.execute()

            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "กรุณาป้อนตัวเลขเป็นจำนวนเต็ม", Toast.LENGTH_LONG).show()
            }


        }
        btn_stop.setOnClickListener {
            count.cancel(true)

        }
    }

    inner class Count : AsyncTask<Void, Void, Void>() {
        override fun onPreExecute() {
            super.onPreExecute()
            btn_stop.isEnabled = true
            btn_ok.isEnabled = false
            editText_num.isEnabled = false
        }

        override fun doInBackground(vararg params: Void?): Void? {
            if (countUp.isChecked) {
                for (i in 0..number) {
                    Thread.sleep(100)
                    runOnUiThread {
                        textView_count.text = i.toString()
                    }
                    if (isCancelled) {
                        break
                    }
                }
            } else {
                for (i in number downTo 0) {
                    Thread.sleep(100)
                    runOnUiThread {
                        textView_count.text = i.toString()
                    }
                    if (isCancelled) {
                        break
                    }
                }
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Toast.makeText(applicationContext, "นับเสร็จแล้ว", Toast.LENGTH_LONG).show()
            btn_stop.isEnabled = false
            btn_ok.isEnabled = true
            editText_num.isEnabled = true


        }

        override fun onCancelled() {
            super.onCancelled()
            Toast.makeText(applicationContext, "หยุดนับแล้ว", Toast.LENGTH_LONG).show()
            btn_stop.isEnabled = false
            btn_ok.isEnabled = true
            editText_num.isEnabled = true


        }

    }
}
