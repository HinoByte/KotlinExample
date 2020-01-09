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
      //  var l0gin = login
      //  l0gin = login.replace("[^+\\d]".toRegex(),"")
     //   var user:User? = map[l0gin]
//        if(l0gin.count{it == '+'} == 1)
//            User.makeUser(user)
//
//        val fullName = user!!.hashCode()
//        println(map)
//        map[l0gin]=User.makeUser(fullName, phone = l0gin)
//        println("${map.entries}")

        //user = map[l0gin]
      //  var firstName = "s"
       // User(firstName, lastName, phone)

  //      val code = user
       // user!!.accessCode = code
    }


 //   fun importUsers(list: List<String>): List<String>{
//        val list = list.joinToString().split(";").toList()
//        val fullName = list[0]
//        val email = list[1]
//        val hashSalt = list[2]
//        return listOf(User.makeUser(fullName, email=email, hashSalt = hashSalt).userInfo)
//            .also { user -> if (!map.containsKey(user.login)) map[user.login] = user TODO СОХРАНЕНИЕ
//            else throw IllegalArgumentException("A user with this email already exists") }
    }
//}

















