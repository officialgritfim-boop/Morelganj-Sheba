package com.example.ui.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object IntentUtils {
    fun dialPhoneNumber(context: Context, phoneNumber: String) {
        val cleanNumber = phoneNumber.replace(Regex("[^0-9+]"), "")
        if (cleanNumber.isBlank()) {
            Toast.makeText(context, "ফোন নম্বর পাওয়া যায়নি", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$cleanNumber")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "কল ডায়াল করতে সমস্যা হয়েছে", Toast.LENGTH_SHORT).show()
        }
    }

    fun openGoogleMaps(context: Context, mapsUrl: String, placeName: String) {
        try {
            val url = if (mapsUrl.isNotBlank() && mapsUrl.startsWith("http")) {
                mapsUrl
            } else {
                "https://maps.google.com/?q=${Uri.encode(placeName + ", Morrelganj, Bagerhat")}"
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "ম্যাপ ওপেন করা যাচ্ছে না", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareInstitution(context: Context, nameBn: String, address: String, phone: String, headPerson: String, mapsUrl: String) {
        try {
            val shareText = """
                📌 $nameBn (মোরেলগঞ্জ সেবা ডিরেক্টরি)
                📍 ঠিকানা: $address
                👤 দায়িত্বে: $headPerson
                📞 যোগাযোগের নম্বর: $phone
                🗺️ গুগল ম্যাপ অবস্থান: $mapsUrl
                
                মোরেলগঞ্জ তথ্য ও সেবা অ্যাপ থেকে শেয়ার করা হয়েছে।
            """.trimIndent()
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, "তথ্য শেয়ার করুন")
            context.startActivity(shareIntent)
        } catch (e: Exception) {
            Toast.makeText(context, "শেয়ার করতে সমস্যা হয়েছে", Toast.LENGTH_SHORT).show()
        }
    }
}
