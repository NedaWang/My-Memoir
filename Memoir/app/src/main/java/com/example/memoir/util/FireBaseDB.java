package com.example.memoir.util;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class FireBaseDB {

    static FirebaseFirestore db;

    public FireBaseDB(){
        firebaseSetup();
    }

    public void firebaseSetup() {
        // [START get_firestore_instance]
        db = FirebaseFirestore.getInstance();
        // [END get_firestore_instance]

        // [START set_firestore_settings]
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        // [END set_firestore_settings]
    }

    public static FirebaseFirestore getDb() {
        return db;
    }

    public static void setDb(FirebaseFirestore db) {
        FireBaseDB.db = db;
    }
}
