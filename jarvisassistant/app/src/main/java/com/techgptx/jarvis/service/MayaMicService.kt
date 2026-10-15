package com.techgptx.jarvis.service
import android.app.*
import android.content.Intent
import android.os.IBinder
import android.speech.*
import android.content.Context
import androidx.core.app.NotificationCompat
import com.techgptx.jarvis.MainActivity
import com.techgptx.jarvis.R
import java.util.*

class MayaMicService : Service(), RecognitionListener {
    private var speech: SpeechRecognizer? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelId = "MAYA_ALWAYS_ON"
        val nm = getSystemService(NotificationManager::class.java)
        nm.createNotificationChannel(NotificationChannel(channelId, "Maya Always ON", NotificationManager.IMPORTANCE_LOW))
        val notif = NotificationCompat.Builder(this, channelId)
           .setContentTitle("Maya - Always Listening - JAD JI")
           .setContentText("🔴 مائیک ہمیشہ ON ہے - کبھی OFF نہیں ہوگا")
           .setSmallIcon(R.drawable.orb_idle)
           .setOngoing(true)
           .build()
        startForeground(1, notif)
        startListening()
        return START_STICKY
    }
    fun startListening(){
        speech?.destroy()
        speech = SpeechRecognizer.createSpeechRecognizer(this)
        speech?.setRecognitionListener(this)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi-IN")
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        speech?.startListening(intent)
    }
    override fun onResults(results: Bundle?) {
        val list = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val text = list?.get(0)?: ""
        // یہاں Maya کا جواب بولے گی
        startListening() // فوراً دوبارہ شروع
    }
    override fun onError(error: Int) { startListening() } // Error پر بھی دوبارہ ON
    override fun onReadyForSpeech(p0: Bundle?) {}
    override fun onBeginningOfSpeech() {}
    override fun onRmsChanged(p0: Float) {}
    override fun onBufferReceived(p0: ByteArray?) {}
    override fun onEndOfSpeech() { startListening() } // ختم ہوتے ہی دوبارہ ON
    override fun onEvent(p0: Int, p1: Bundle?) {}
    override fun onPartialResults(p0: Bundle?) {}
    override fun onBind(intent: Intent?): IBinder? = null
}
