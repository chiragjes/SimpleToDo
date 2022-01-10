package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodo.TaskItemAdapter.OnLongClickListener
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //Remove the item
                listOfTasks.removeAt(position)
                //Notify the adapter that dataset has changed
                adapter.notifyDataSetChanged()
                saveItems()
            }

        }
        loadItems()
        //Getting the recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        //connecting the recyclerView and the adapter
        recyclerView.adapter = adapter
        //Adding the layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Better code readability
        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        //Detecting when the button is clicked
        findViewById<Button>(R.id.button).setOnClickListener {
            //Getting the text the user had entered
            val userInputtedTask = inputTextField.text.toString()
            //Add the said task to the list of Tasks
            listOfTasks.add(userInputtedTask)
            //Notify the adapter that the dataset has changed
            adapter.notifyItemInserted(listOfTasks.size - 1)
            //Set the add task bar to empty string
            inputTextField.setText("")
            saveItems()
        }
    }
    // Save the data user has inputted
    // Save the data by reading and writing from a file
    fun getDataFile() : File{

        return File(filesDir, "data.txt")
    }


    // Load the items by reading every line in the file
    fun loadItems(){
        try {
            listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(),Charset.defaultCharset())
        } catch (ioException:IOException){
            ioException.printStackTrace()
        }


    }

    // Save items by writing them into our data file
    fun saveItems() {
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        }
        catch (ioException:IOException){
            ioException.printStackTrace()
        }


    }
}