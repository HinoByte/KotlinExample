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
        val l0gin = login.replace("[^+\\d]".toRegex(),"")
     //   var user:User? = map[l0gin]
//        if(l0gin.count{it == '+'} == 1)
//            User.makeUser(user)
//
//        val fullName = user!!.hashCode()
//        println(map)
      //  map[l0gin]=User.makeUser(fullName, phone = l0gin)
//        println("${map.entries}")

        //user = map[l0gin]
      //  var firstName = "s"
       // User(firstName, lastName, phone)

  //      val code = user
       // user!!.accessCode = code
       // print("${map[l0gin]?.accessCode}")
       // val k = map[l0gin]?.accessCode
                                          //map[l0gin]?.accessCode = map[l0gin]?.generateAccesCode() //OK v1
                    map[l0gin]?.accessCode = map[l0gin]?.makeAccessCode() //OK v2
        //map[l0gin].makeUser(fullName, email=email, password = password)
     //   val k = User.makeUser(map[l0gin]!!.fullName, phone = l0gin)
        //make user. со значениями из мапы.
       // print("${k.userInfo}")
       // println("")
       // map[l0gin] = User.makeUser(map[l0gin]!!.fullName, phone = l0gin)
    }


    fun importUsers(list: List<String>): List<User> {
        val list = list.joinToString().split(";").toList()
        val fullName = list[0]
        var email:String? = list[1]
        val hashSalt = list[2]
        var phone:String? = list[3]
        if (phone!!.isBlank()) phone = null
        if (email!!.isBlank()) email = null
        val user = User.makeUser(fullName, email=email, hashSalt = hashSalt, phone = phone)
        if (!map.containsKey(user.login)) map[user.login] = user
        return listOf(user)
            // .also {
            //      it
          //  }
    }

}
//            .also { user -> if (!map.containsKey(user.login)) map[user.login] = user
//            else throw IllegalArgumentException("A user with this email already exists") }

//}

















