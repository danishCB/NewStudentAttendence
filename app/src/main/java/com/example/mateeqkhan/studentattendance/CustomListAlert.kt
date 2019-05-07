package com.example.mateeqkhan.studentattendance

import android.app.AlertDialog
import android.content.Context

/**
 * Created by M.Ateeq khan on 9/14/2018.
 */
data class CustomListAlert(var context:Context?, var itemList: Array<String>, var alertTitle: String ) {


    fun showCustomAlert(completion: (CharSequence) -> Unit) {
        val builder = AlertDialog.Builder(context);



        builder.setTitle(alertTitle).setItems(itemList,
                {
                    dialog, itemIndex ->
                    completion.invoke(itemList[itemIndex])
                    dialog.dismiss()
                })

        builder.create().show()

    }




}