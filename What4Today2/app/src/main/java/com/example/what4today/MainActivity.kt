package com.example.what4today

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Filter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.what4today.ui.theme.What4TodayTheme

class MainActivity : ComponentActivity() {

    //These layouts represent different screens in ONE Activity
    lateinit var welcomeLayout: LinearLayout
    lateinit var loginLayout: LinearLayout
    lateinit var categoryLayout: LinearLayout
    lateinit var mealLayout: LinearLayout

    //Welcome screen image
    lateinit var welcomeImage: ImageView

    //Login inputs
    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var loginBtn: Button

    //Category Initialisation Buttons
    lateinit var btnBreakfast: Button
    lateinit var btnLunch: Button
    lateinit var btnSnack: Button
    lateinit var btnDinner: Button

    //Meal Screen Components
    lateinit var spinnerFilter: Spinner
    lateinit var mealList: ListView
    lateinit var recipeText: TextView

    //Stores meals and their recipes
    val meals = mutableListOf<String>()
    val recipes = hashMapOf<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Link Layouts/Designs to code
        welcomeLayout = findViewById(R.id.welcomeLayout)
        loginLayout = findViewById(R.id.loginLayout)
        categoryLayout = findViewById(R.id.categoryLayout)
        mealLayout = findViewById(R.id.mealLayout)

        //Link welcome image
        welcomeImage = findViewById(R.id.welcomeImage)

        //Link Login components
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        loginBtn = findViewById(R.id.loginBtn)

        //Link category buttons
        btnBreakfast = findViewById(R.id.btnBreakfast)
        btnLunch = findViewById(R.id.btnLunch)
        btnSnack = findViewById(R.id.btnSnacks)
        btnDinner = findViewById(R.id.btnDinner)

        //Link Meal components
        spinnerFilter = findViewById(R.id.spinnerFilter)
        mealList = findViewById(R.id.mealList)
        recipeText = findViewById(R.id.recipeText)

        //SHOW ONLY WELCOME SCREEN FIRST
        welcomeLayout.visibility = View.VISIBLE
        loginLayout.visibility = View.GONE
        categoryLayout.visibility = View.GONE
        mealLayout.visibility = View.GONE

        //When user clicks welcome image → go to login screen
        welcomeImage.setOnClickListener {
            welcomeLayout.visibility = View.GONE
            loginLayout.visibility = View.VISIBLE
        }

        //Spinner options
        val filters = arrayOf("All", "High Protein", "High Fibre")

        spinnerFilter.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            filters
        )

        //Login Button Logic
        loginBtn.setOnClickListener {

            if (username.text.toString() == "Londi" &&
                password.text.toString() == "Londi2026"
            ) {

                loginLayout.visibility = View.GONE
                categoryLayout.visibility = View.VISIBLE

            } else {
                Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT).show()
            }
        }

        //Category Buttons
        btnBreakfast.setOnClickListener { loadMeals("Breakfast") }
        btnLunch.setOnClickListener { loadMeals("Lunch") }
        btnSnack.setOnClickListener { loadMeals("Snack") }
        btnDinner.setOnClickListener { loadMeals("Dinner") }

        //Meal click
        mealList.setOnItemClickListener { _, _, position, _ ->
            val meal = meals[position]
            recipeText.text = recipes[meal]
        }
    }

    //Function to load meals based on selected category
    private fun loadMeals(category: String) {

        categoryLayout.visibility = View.GONE
        mealLayout.visibility = View.VISIBLE

        meals.clear()
        recipes.clear()

        val filter = spinnerFilter.selectedItem.toString()

        //Breakfast
        if (category == "Breakfast") {

            if (filter == "High Protein" || filter == "All") {
                meals.add("Egg Omelette")
                recipes["Egg Omelette"] = "Eggs + spinach. Cook for 5 minutes"
            }

            if (filter == "High Fibre" || filter == "All") {
                meals.add("Oatmeal Bowl")
                recipes["Oatmeal Bowl"] =
                    "Oats + milk + fruits. Cook for 10 minutes then add toppings"
            }
        }

        //Lunch
        if (category == "Lunch") {
            meals.add("Grilled Chicken Salad")
            recipes["Grilled Chicken Salad"] = "Chicken + greens + dressing"
        }

        //Snack
        if (category == "Snack") {
            meals.add("Greek Yoghurt")
            recipes["Greek Yoghurt"] = "Yoghurt + honey + nuts"
        }

        //Dinner
        if (category == "Dinner") {
            meals.add("Salmon with Veggies")
            recipes["Salmon with Veggies"] = "Bake salmon + vegetables"
        }

        mealList.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            meals
        )
    }
}