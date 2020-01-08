package ru.skillbranch.kotlinexample

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
        var l0gin = login                                                                 //?
        if(l0gin.count{it == '+'} == 1) l0gin = login.replace("[^+\\d]".toRegex(),"") //?
        return map[l0gin.trim()]?.run {
            if(checkPassword(password) || checkSMS(password)) this.userInfo
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
        var l0gin = login
        l0gin = login.replace("[^+\\d]".toRegex(),"")
        var user:User? = map[l0gin]
//        if(l0gin.count{it == '+'} == 1)
//            User.makeUser(user)
//
        val fullName = user!!.fullName
        User.makeUser(fullName, phone = l0gin)
        //user = map[l0gin]
  //      val code = user
       // user!!.accessCode = code
    }

}

















