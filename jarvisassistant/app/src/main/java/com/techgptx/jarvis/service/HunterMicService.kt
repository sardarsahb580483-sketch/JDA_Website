package com.techgptx.jarvis.service
import android.app.*
import android.content.Intent
import android.os.IBinder
import android.speech.*
import androidx.core.app.NotificationCompat
import com.techgptx.jarvis.R
import java.util.*

class HunterMicService : Service(), RecognitionListener {
    private var speech: SpeechRecognizer? = null
    private val channelId = "HUNTER_ALWAYS_ON"
    
    override fun onCreate() { super.onCreate(); createChannel() }
    
    private fun createChannel(){
        val nm = getSystemService(NotificationManager::class.java)
        nm.createNotificationChannel(NotificationChannel(channelId, "Hunter AI Always ON", NotificationManager.IMPORTANCE_LOW))
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notif = NotificationCompat.Builder(this, channelId)
            .setContentTitle("🎯 Hunter AI - Always Listening - JAD JI")
            .setContentText("🔴 Mic hamesha ON hai - 12k Channel - 300 Reacts")
            .setSmallIcon(R.drawable.orb_idle)
            .setOngoing(true)
            .build()
        startForeground(1, notif)
        startListening()
        return START_STICKY
    }
    
    fun startListening(){
        try{ speech?.destroy() }catch(_:Exception){}
        speech = SpeechRecognizer.createSpeechRecognizer(this)
        speech?.setRecognitionListener(this)
        val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply{
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi-IN")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 3000)
        }
        speech?.startListening(i)
    }
    
    override fun onResults(results: Bundle?) {
        val txt = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0) ?: ""
        // Yahan Hunter Logic chalega
        if(txt.contains("channel",true)||txt.contains("whatsapp",true)){
            // Open Channel intent
        }
        startListening() // Foran dubara ON
    }
    override fun onError(error: Int) { startListening() }
    override fun onEndOfSpeech() { startListening() }
    override fun onReadyForSpeech(p0: Bundle?) {}
    override fun onBeginningOfSpeech() {}
    override fun onRmsChanged(p0: Float) {}
    override fun onBufferReceived(p0: ByteArray?) {}
    override fun onEvent(p0: Int, p1: Bundle?) {}
    override fun onPartialResults(p0: Bundle?) {}
    override fun onBind(intent: Intent?): IBinder? = null
}
