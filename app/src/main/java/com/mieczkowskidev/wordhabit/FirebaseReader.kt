package com.mieczkowskidev.wordhabit

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by Patryk Mieczkowski on 18.03.2018
 */
class FirebaseReader {

    companion object {
        private val TAG = FirebaseReader::class.java.simpleName
    }

    fun readFromFirebase() {

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val myRef = firebaseDatabase.getReference("animals")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val counter = dataSnapshot.childrenCount
                Log.d(TAG, "child size: $counter")
//                val value = dataSnapshot.getValue(GenericTypeIndicator<List<String>>())
//                Log.d(TAG, "Value is: " + value!!)
                for (ds: DataSnapshot in dataSnapshot.children) {
                    Log.d(TAG, "child list: " + ds.value.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}