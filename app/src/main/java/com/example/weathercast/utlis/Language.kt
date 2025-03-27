package com.example.weathercast.utlis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.util.Log
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.language.bm.Languages
import java.util.Locale

//fun restartActivity(context: Context) {
//    Log.i("TAG", "restartActivity:  iiiitttttt works " )
//    val intent = (context as? Activity)?.intent
//    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//    context.startActivity(intent)
//    (context as? Activity)?.finish()
//}
fun restartActivity(context: Context) {
    val packageManager = context.packageManager
    val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        ?.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }

    if (intent != null) {
        context.startActivity(intent)
        if (context is Activity) {
            context.finish()
        }
    } else {
        Log.e("TAG", "Failed to restart: Launch intent is null")
    }
}


 fun applyLanguage(sharedPreferences : SharedPreferences, resources: Resources){

    val language = sharedPreferences.getString(Constants.LANGUAGE , Constants.Emglish_PARM)
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = resources.configuration
    config.setLocale(locale)
    Log.i("TAG", "applyLanguage:  we are heeeeeeer ")
    resources.updateConfiguration(config, resources.displayMetrics)
}

fun convertToArabicNumbers(number: String): String {
    val arabicDigits = arrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
    return number.map { if (it.isDigit()) arabicDigits[it.digitToInt()] else it }.joinToString("")
}

fun formatNumberBasedOnLanguage(number: String): String {
    val language = Locale.getDefault().language
    Log.i("langage", "formatNumberBasedOnLanguage: ${language}")
  return if (language == "ar") convertToArabicNumbers(number) else number
}