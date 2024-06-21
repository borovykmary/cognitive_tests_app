package com.example.cognittiveassesmenttests

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem

import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognittiveassesmenttests.adapters.TestRecordAdapterAdminMA
import com.example.cognittiveassesmenttests.dataClasses.TestRecordMAAdmin
import com.example.cognittiveassesmenttests.helpers.ConfirmPopupAdminFragment

import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cognittiveassesmenttests.adapters.TestRecordAdapterAdminMA
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


import com.google.firebase.firestore.FirebaseFirestore

class AdminActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout_admin)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        val recyclerView = findViewById<RecyclerView>(R.id.testRecordsRecycleViewMA)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val testRecordsAdminMA = mutableListOf<TestRecordMAAdmin>()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AdminFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }


        // Fetch user data from Firebase
        val user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()
        db.collection("Users").get().addOnSuccessListener { users ->
            val testData = mutableListOf<Map<String, Any>>()
            for (user in users) {
                val userId = user.id
                db.collection("Users").document(userId).collection("TestMA").get().addOnSuccessListener { tests ->
                    for (test in tests) {
                        val dataWithId = test.data.toMutableMap()
                        // Check if the document has the field "MEMORY"
                        if (dataWithId.containsKey("MEMORY")) {
                            continue // Skip this document
                        }
                        dataWithId["id"] = test.id // Add the document ID to the data map
                        testData.add(dataWithId)
                        Log.d("DATAACTIVITY", "DATA: " + dataWithId["id"])
                        val id = test.id
                        val address = test.getString("Address")
                        val address1 = test.getString("Address1")
                        val address1Repeat = test.getString("Address1Repeat")
                        val addressRepeat = test.getString("AddressRepeat")
                        val animals = test.getString("Animals")
                        val day = test.getString("Day")
                        val month = test.getString("Month")
                        val name = test.getString("Name")
                        val name1 = test.getString("Name1")
                        val name1Repeat = test.getString("Name1Repeat")
                        val nameRepeat = test.getString("NameRepeat")
                        val year = test.getString("Year")
                        val testDate = test.getString("DateTime")
                        val testTime = test.getString("Time")
                        val drawingUrl = test.getString("DrawingImageURL")
                        val testRecord = TestRecordMAAdmin(id, userId, address!!, address1!!, address1Repeat!!, addressRepeat!!, animals!!, day!!, month!!, name!!, name1!!, name1Repeat!!, nameRepeat!!, year!!, testDate!!, testTime!!, drawingUrl!!)
                        testRecordsAdminMA.add(testRecord)
                    }
                    val adapter = TestRecordAdapterAdminMA(testRecordsAdminMA, supportFragmentManager)
                    recyclerView.adapter = adapter
                }

        db.collection("Users").document(user?.uid!!).get().addOnSuccessListener { document ->
            val name = document.getString("name")

            // Update userNameText in nav_header
            val headerView = navigationView.getHeaderView(0)
            val userNameText = headerView.findViewById<TextView>(R.id.userNameText)
            userNameText.text = name
        }

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AdminFragment()).commit()
            R.id.nav_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed() {
        super.onBackPressed()
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}

class AdminMAPopupFragment: DialogFragment(){
    companion object {
        fun newInstance(testRecord: TestRecordMAAdmin): AdminMAPopupFragment {
            val fragment = AdminMAPopupFragment()
            val args = Bundle()
            args.putString("id", testRecord.id)
            args.putString("userID", testRecord.userID)
            args.putString("Address", testRecord.address)
            args.putString("Address1", testRecord.address1)
            args.putString("Address1Repeat", testRecord.address1Repeat)
            args.putString("AddressRepeat", testRecord.addressRepeat)
            args.putString("Animals", testRecord.animals)
            args.putString("Day", testRecord.day)
            args.putString("Month", testRecord.month)
            args.putString("Name", testRecord.name)
            args.putString("Name1", testRecord.name1)
            args.putString("Name1Repeat", testRecord.name1Repeat)
            args.putString("NameRepeat", testRecord.nameRepeat)
            args.putString("Year", testRecord.year)
            args.putString("DateTime", testRecord.testDate)
            args.putString("Time", testRecord.testTime)
            args.putString("DrawingImageURL", testRecord.drawingUrl)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater: LayoutInflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.popup_admin_ma, null)

            // Initialize your views here
            val address1: TextView = view.findViewById(R.id.address1_answer)
            val address2: TextView = view.findViewById(R.id.address2_answer)
            val address1Repeat: TextView = view.findViewById(R.id.address1recall_answer)
            val address2Repeat: TextView = view.findViewById(R.id.address2recall_answer)
            val animals: TextView = view.findViewById(R.id.animals_answer)
            val date: TextView = view.findViewById(R.id.date_answer)
            val day: TextView = view.findViewById(R.id.day_answer)
            val month: TextView = view.findViewById(R.id.month_answer)
            val name1: TextView = view.findViewById(R.id.name1_answer)
            val name2: TextView = view.findViewById(R.id.name2_answer)
            val name1Repeat: TextView = view.findViewById(R.id.name1recall_answer)
            val name2Repeat: TextView = view.findViewById(R.id.name2recall_answer)
            val time: TextView = view.findViewById(R.id.time)
            val year: TextView = view.findViewById(R.id.year_answer)
            val input1: Spinner = view.findViewById(R.id.input1)
            val input2: Spinner = view.findViewById(R.id.input2)
            val input3: Spinner = view.findViewById(R.id.input3)
            val input4: Spinner = view.findViewById(R.id.input4)
            val input5: Spinner = view.findViewById(R.id.input5)
            val confirmButton: Button = view.findViewById(R.id.confirm_button)
            val downloadButton: Button = view.findViewById(R.id.download_button)
            val closeButton: Button = view.findViewById(R.id.closeButtonAdmin)

            address1.text = "Address 1: " + arguments?.getString("Address")
            address2.text = "Address 2: " + arguments?.getString("Address1")
            address1Repeat.text = "Address 1: " + arguments?.getString("Address1Repeat")
            address2Repeat.text = "Address 2: " + arguments?.getString("AddressRepeat")
            animals.text = "Animals: " + arguments?.getString("Animals")
            date.text = "Date: " + arguments?.getString("Date")
            day.text = "Day: " + arguments?.getString("Day")
            month.text = "Month: " + arguments?.getString("Month")
            name1.text = "Name 1: " + arguments?.getString("Name")
            name2.text = "Name 2: " + arguments?.getString("Name1")
            name1Repeat.text = "Name 1: " + arguments?.getString("Name1Repeat")
            name2Repeat.text = "Name 2: " + arguments?.getString("NameRepeat")
            time.text = "Total Time: " + arguments?.getString("Time")
            year.text = "Year: " + arguments?.getString("Year")

            // Create an ArrayAdapter using the integer array and a default spinner layout
            val adapter1 = ArrayAdapter.createFromResource(
                it,
                R.array.input1_options,
                android.R.layout.simple_spinner_item
            )
            // Specify the layout to use when the list of choices appears
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            input1.adapter = adapter1
            // Set the default value
            input1.setSelection(0)

            // Repeat the above steps for the other spinners
            val adapter2 = ArrayAdapter.createFromResource(
                it,
                R.array.input2_options,
                android.R.layout.simple_spinner_item
            )
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            input2.adapter = adapter2
            input2.setSelection(0)

            val adapter3 = ArrayAdapter.createFromResource(
                it,
                R.array.input3_options,
                android.R.layout.simple_spinner_item
            )
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            input3.adapter = adapter3
            input3.setSelection(0)

            val adapter4 = ArrayAdapter.createFromResource(
                it,
                R.array.input4_options,
                android.R.layout.simple_spinner_item
            )
            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            input4.adapter = adapter4
            input4.setSelection(0)

            val adapter5 = ArrayAdapter.createFromResource(
                it,
                R.array.input5_options,
                android.R.layout.simple_spinner_item
            )
            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            input5.adapter = adapter5
            input5.setSelection(0)

            // Set your onClickListeners here
            downloadButton.setOnClickListener {
                val url = arguments?.getString("DrawingImageURL")
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }

            confirmButton.setOnClickListener {
                // Create an instance of the dialog fragment
                val dialog = ConfirmPopupAdminFragment(
                    arguments?.getString("id").toString(),
                    arguments?.getString("userID").toString(),
                    input1,
                    input2,
                    input3,
                    input4,
                    input5
                )
                // Show the dialog
                dialog.show(parentFragmentManager, "ConfirmDialog")
            }

            closeButton.setOnClickListener {
                dismiss()
            }

            builder.setView(view)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
            dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}