package ru.skillbranch.kotlinexample

import androidx.core.text.isDigitsOnly
import java.lang.IllegalArgumentException

object UserHolder {
    private val map = mutableMapOf<String, User>()

    fun registerUser(
        fullName:String,
        email:String,
        password:String
    ):User{
        if (map[email] != null) throw IllegalArgumentException("A user with this email already exists")
        return User.makeUser(fullName, email=email, password = password)
            .also { user -> map[user.login] = user }
    }

    fun loginUser(login: String, password: String): String?{
        return map[login.trim()]?.run {
            if(checkPassword(password)) this.userInfo
            else null
        }
    }


    fun registerUserByPhone(
        fullName: String,
        rawPhone: String
    ):User{
      //  if (rawPhone.length !=12) throw IllegalArgumentException("Enter a valid phone number starting with a + and containing 11 digits")
      //  var numbers = rawPhone.substring(1)
   //     if (!numbers.isDigitsOnly()) throw IllegalArgumentException("Enter a valid phone number starting with a + and containing 11 digits")
//        if (!rawPhone.startsWith('+')) throw IllegalArgumentException("Enter a valid phone number starting with a + and containing 11 digits")
//        val numbers = "123456789"
//        for (i in 1..10) {
//            for (j in 0..8)
//           if (rawPhone[i] != numbers[j]) throw IllegalArgumentException("Enter a valid phone number starting with a + and containing 11 digits")
//        }
        //var s:String
        //s = rawPhone.replace("[^+\\d]".toRegex(),"")
       // if(!s.matches("[+]\\d{11}".toRegex())) throw IllegalArgumentException("Enter a valid phone number starting with a + and containing 11 digits")


        return User.makeUser(fullName, phone = rawPhone)
            .also { user -> map[user.login] = user }
    }

    fun requestAccessCode(login: String){


    }

}

















