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
        return User.makeUser(fullName, email=email, password = password)
            .also { user -> if (!map.containsKey(user.login)) map[user.login] = user
                    else throw IllegalArgumentException("A user with this email already exists") }
    }

    fun loginUser(login: String, password: String): String?{
        return map[login.trim()]?.run {
            if(checkPassword(password)) this.userInfo
           // if(accessCode == password) this.userInfo
            else null
        }
    }


    fun registerUserByPhone(
        fullName: String,
        rawPhone: String
    ):User{
        return User.makeUser(fullName, phone = rawPhone)
            .also { user -> if (!map.containsKey(user.login)) map[user.login] = user
                    else throw IllegalArgumentException("A user with this phone already exists") }
    }

    fun requestAccessCode(login: String){

    }

}

















