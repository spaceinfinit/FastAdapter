package com.mikepenz.fastadapter.app

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.app.databinding.ActivitySampleBinding
import com.mikepenz.fastadapter.app.items.CheckBoxSampleItem
import com.mikepenz.fastadapter.select.SelectExtension
import com.mikepenz.fastadapter.select.getSelectExtension
import java.util.*

class CheckBoxSampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySampleBinding

    //save our FastAdapter
    private lateinit var fastItemAdapter: FastItemAdapter<CheckBoxSampleItem>
    private lateinit var selectExtension: SelectExtension<CheckBoxSampleItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        // Handle Toolbar
        setSupportActionBar(binding.toolbar)

        //create our FastAdapter which will manage everything
        fastItemAdapter = FastItemAdapter()
        selectExtension = fastItemAdapter.getSelectExtension()
        selectExtension.isSelectable = true

        //configure our fastAdapter
        fastItemAdapter.onClickListener = { v: View?, _: IAdapter<CheckBoxSampleItem>, item: CheckBoxSampleItem, _: Int ->
            v?.let { Toast.makeText(v.context, item.name?.getText(v.context), Toast.LENGTH_LONG).show() }
            false
        }
        fastItemAdapter.onPreClickListener = { _: View?, _: IAdapter<CheckBoxSampleItem>, _: CheckBoxSampleItem, _: Int ->
            true // consume otherwise radio/checkbox will be deselected
        }

        fastItemAdapter.addEventHook(CheckBoxSampleItem.CheckBoxClickEvent())

        //get our recyclerView and do basic setup
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.itemAnimator = DefaultItemAnimator()
        binding.rv.adapter = fastItemAdapter

        //fill with some sample data
        var x = 0
        val items = ArrayList<CheckBoxSampleItem>()
        for (s in ALPHABET) {
            val count = Random().nextInt(20)
            for (i in 1..count) {
                val item = CheckBoxSampleItem().withName("$s Test $x")
                item.identifier = (100 + x).toLong()
                items.add(item)
                x++
            }
        }
        fastItemAdapter.add(items)

        //restore selections (this has to be done after the items were added
        fastItemAdapter.withSavedInstanceState(savedInstanceState)

        //set the back arrow in the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(false)
    }

    override fun onSaveInstanceState(_outState: Bundle) {
        var outState = _outState
        //add the values which need to be saved from the adapter to the bundle
        outState = fastItemAdapter.saveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //handle the click on the back arrow click
        return when (item.itemId) {
            android.R.id.home -> {
                Toast.makeText(applicationContext, "selections = " + selectExtension.selections, Toast.LENGTH_LONG).show()
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private val ALPHABET = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
    }
}
