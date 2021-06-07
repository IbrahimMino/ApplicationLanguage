package com.example.applicationlanguage

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLanguage()
        setContentView(R.layout.activity_main)


        Log.d("TAO", "onCreate: ${getLatestCheckedId()}")

        val actionbar = supportActionBar
        actionbar?.title = resources.getString(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_bar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.language_item){
            openLanguageDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    fun openLanguageDialog(){
        val language = arrayOf("English","Русский","Uzbek")

        val builder = AlertDialog.Builder(this).setTitle(getString(R.string.choose_language))
            .setSingleChoiceItems(language,getLatestCheckedId()){
                dialog, i ->
                if (i == 0){
                    setLanguageId(i)
                 setLanguage("en")
                    recreate()
                }else{
                    if (i == 1){
                        setLanguageId(i)
                    setLanguage("ru")
                        recreate()
                }else{
                        if (i == 2){
                            setLanguageId(i)
                        setLanguage("uz")
                        recreate()
                    }
                    }
                }
                 dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    fun setLanguage(lang:String){
    val locale = Locale(lang)
        Locale.setDefault(locale)
        val config =  Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)
        val editor = getSharedPreferences("language_settings", Context.MODE_PRIVATE).edit()
        editor.putString("language",lang)

        editor.apply()

    }


    fun getLanguage(){
      val preferences = getSharedPreferences("language_settings",Context.MODE_PRIVATE)
        val language = preferences.getString("language","en")
        setLanguage(language!!)
    }

    fun getLatestCheckedId():Int{
        val preferences = getSharedPreferences("languageId",Context.MODE_PRIVATE)
        return preferences.getInt("checkedId",0)
    }

    fun setLanguageId(checkesId:Int){
        val editor = getSharedPreferences("languageId", Context.MODE_PRIVATE).edit()
        editor.putInt("checkedId",checkesId)
        editor.apply()
    }
}