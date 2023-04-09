package cat.copernic.disciplinevents.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cat.copernic.disciplinevents.R
import java.util.*
import kotlin.concurrent.schedule


class Splashard : AppCompatActivity() {

    var timer = Timer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //HIDE APP BAR
        if (supportActionBar != null)
            supportActionBar!!.hide()

        setContentView(R.layout.activity_splashard)
        timer.schedule(1000) {
            var intent = Intent(this@Splashard, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}