package com.example.cognittiveassesmenttests.MiniAceTest

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.cognittiveassesmenttests.MainActivity
import com.example.cognittiveassesmenttests.MiniAceTest1
import com.example.cognittiveassesmenttests.MiniAceTest2
import com.example.cognittiveassesmenttests.MiniAceTest3
import com.example.cognittiveassesmenttests.MiniAceTest4
import com.example.cognittiveassesmenttests.MiniAceTest5
import com.example.cognittiveassesmenttests.R
import com.example.cognittiveassesmenttests.helpers.showConfirmPopup
import com.example.cognittiveassesmenttests.helpers.showConfirmPopupMA
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import java.io.ByteArrayOutputStream
import java.util.UUID
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MiniAceTestActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var imageViewCounter: ImageView
    private var data: ByteArray = byteArrayOf()
    private val dataMap = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mini_ace_test)



        viewPager = findViewById(R.id.viewPagerQuestions)
        viewPager.adapter = MiniAceTestAdapter(this)
        viewPager.isUserInputEnabled = false

        imageViewCounter = findViewById(R.id.imageViewCounter)


        val nextButton: ImageView = findViewById(R.id.next_button_image)
        val buttonTestSubmit: Button = findViewById(R.id.buttonTestSubmit)
        buttonTestSubmit.isEnabled = false
        nextButton.setOnClickListener {
        val currentFragment = (viewPager.adapter as MiniAceTestAdapter).getCurrentFragment(viewPager.currentItem)
            val view = currentFragment?.view
            Log.d("MiniAce", "Current fragment view:" + view)
            if (view != null) {
                Log.d("MiniAce", "View")
                when (currentFragment) {
                    is MiniAceTest1 -> {
                        Log.d("MiniAce", "Data for Mini1: " + dataMap)
                        val editTextDay = view.findViewById<EditText>(R.id.editTextDay)
                        val editTextDate = view.findViewById<EditText>(R.id.editTextDate)
                        val editTextMonth = view.findViewById<EditText>(R.id.editTextMonth)
                        val editTextYear = view.findViewById<EditText>(R.id.editTextYear)

                        dataMap["Day"] = editTextDay.text.toString()
                        dataMap["Date"] = editTextDate.text.toString()
                        dataMap["Month"] = editTextMonth.text.toString()
                        dataMap["Year"] = editTextYear.text.toString()
                    }

                    is MiniAceTest2 -> {
                        Log.d("MiniAce", "Data for Mini2: " + dataMap)
                        val editTextFieldName = view.findViewById<EditText>(R.id.editTextName)
                        val editTextFieldAddress = view.findViewById<EditText>(R.id.editTextAddress)
                        val editTextFieldName1 = view.findViewById<EditText>(R.id.editTextName1)
                        val editTextFieldAddress1 =
                            view.findViewById<EditText>(R.id.editTextAddress1)

                        dataMap["Name"] = editTextFieldName.text.toString()
                        dataMap["Address"] = editTextFieldAddress.text.toString()
                        dataMap["Name1"] = editTextFieldName1.text.toString()
                        dataMap["Address1"] = editTextFieldAddress1.text.toString()
                    }

                    is MiniAceTest3 -> {
                        Log.d("MiniAce", "Data for Mini3: " + dataMap)
                        val editTextAnimals = view.findViewById<EditText>(R.id.editTextAnimals)

                        dataMap["Animals"] = editTextAnimals.text.toString()
                    }

                    is MiniAceTest4 -> {
                        Log.d("MiniAce", "Data for Mini4: " + dataMap)
                        val drawingView = view.findViewById<com.example.cognittiveassesmenttests.helpers.DrawingView>(R.id.drawingView)
                        val bitmap = drawingView.saveCanvas()

                        // Convert the Bitmap to a byte array
                        val baos = ByteArrayOutputStream()
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                        data = baos.toByteArray()

                    }

                    is MiniAceTest5 -> {
                        Log.d("MiniAce", "Data for Mini5: " + dataMap)
                        val editTextFieldNameRepeat =
                            view.findViewById<EditText>(R.id.editTextNameRepeat)
                        val editTextFieldAddressRepeat =
                            view.findViewById<EditText>(R.id.editTextAddressRepeat)
                        val editTextFieldName1Repeat =
                            view.findViewById<EditText>(R.id.editTextName1Repeat)
                        val editTextFieldAddress1Repeat =
                            view.findViewById<EditText>(R.id.editTextAddress1Repeat)

                        dataMap["NameRepeat"] = editTextFieldNameRepeat.text.toString()
                        dataMap["AddressRepeat"] = editTextFieldAddressRepeat.text.toString()
                        dataMap["Name1Repeat"] = editTextFieldName1Repeat.text.toString()
                        dataMap["Address1Repeat"] = editTextFieldAddress1Repeat.text.toString()
                    }
                }
            }
            val currentItem = viewPager.currentItem
            if (currentItem < 4) { // Replace 4 with the number of fragments - 1
                viewPager.setCurrentItem(currentItem + 1)
                updateCounterImage(currentItem + 1)
                Log.d("MiniAce", "Data: " + dataMap)
            } else {
                nextButton.isEnabled = false
                buttonTestSubmit.isEnabled = true
            }
        }


            showConfirmPopup(R.id.MiniAceBackButton, this, R.id.MiniAceTestActivity)

            val textViewTime = findViewById<TextView>(R.id.textViewTime)

            // Initialize a new Handler instance
            val handler = Handler(Looper.getMainLooper())
            var secondsElapsed = 0

            val runnable = object : Runnable {
                override fun run() {
                    val minutes = secondsElapsed / 60
                    val seconds = secondsElapsed % 60
                    textViewTime.text = String.format("%02d:%02d", minutes, seconds)
                    secondsElapsed++
                    handler.postDelayed(this, 1000)
                }
            }

            // Start the timer
            handler.postDelayed(runnable, 1000)

        buttonTestSubmit.setOnClickListener {

            Log.e("Firebase", "Upload started 1")
            // Create a reference to Firebase Storage where you want to store the image
            val storageRef = FirebaseStorage.getInstance().reference
            val imagesRef = storageRef.child("images/${UUID.randomUUID()}.jpg")
            Log.e("Firebase", "Upload started 2")

            // Upload the byte array to Firebase Storage
            val uploadTask = imagesRef.putBytes(data)
            uploadTask.addOnFailureListener {
                    exception ->
                // Handle unsuccessful uploads
                Log.e("Firebase", "Upload failed", exception)
                if (exception is StorageException) {
                    val errorCode = exception.errorCode
                    val httpResult = exception.httpResultCode
                    Log.e("Firebase", "Error code: $errorCode, HTTP result: $httpResult")
                }

                // Handle unsuccessful uploads
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // Get the download URL of the image
                imagesRef.downloadUrl.addOnSuccessListener { uri ->
                    // Store the download URL in Firestore
                    val downloadUrl = uri.toString()
                    Log.d("Firebase", "Download URL: $downloadUrl")
                     val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                     val currentDate = sdf.format(Date())
                    dataMap["DateTime"] = currentDate
                    dataMap["DrawingImageURL"] = downloadUrl
                    dataMap["Time"] = textViewTime.text.toString()
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    if (userId != null) {
                        val db = FirebaseFirestore.getInstance()
                        db.collection("Users").document(userId).collection("TestMA").document().set(dataMap)
                            .addOnSuccessListener { Log.d("Firestore", "DocumentSnapshot successfully written!") }
                            .addOnFailureListener { e -> Log.w("Firestore", "Error writing document", e) }
                    }
                }
            }
            // Navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        }

        private fun updateCounterImage(counter: Int) {
            val drawableRes = when (counter) {
                0 -> R.drawable.number_quiz_1
                1 -> R.drawable.number_quiz_2
                2 -> R.drawable.number_quiz_3
                3 -> R.drawable.number_quiz_4
                4 -> R.drawable.number_quiz_5
                else -> throw IllegalStateException("Invalid counter for updating image")
            }
            imageViewCounter.setImageResource(drawableRes)
        }

    }

