package com.techgptx.jarvis
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.techgptx.jarvis.service.HunterMicService
import android.webkit.WebView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val web = WebView(this)
        web.settings.javaScriptEnabled = true
        web.loadUrl("https://sardarsahb580483-sketch.github.io/JDA_Website/hunter-pro.html")
        setContentView(web)
        
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA), 101)
        } else {
            startService(Intent(this, HunterMicService::class.java))
        }
    }
    override fun onRequestPermissionsResult(code: Int, perms: Array<String>, res: IntArray){
        super.onRequestPermissionsResult(code, perms, res)
        if(code==101) startService(Intent(this, HunterMicService::class.java))
    }
}
