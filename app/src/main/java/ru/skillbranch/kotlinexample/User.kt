package ru.skillbranch.kotlinexample

import androidx.annotation.VisibleForTesting
import java.lang.IllegalArgumentException
import java.lang.StringBuilder
import java.math.BigInteger
import java.security.MessageDigest
import java.security.SecureRandom

class User private constructor(
    private val firstName: String,
    private val lastName: String?,
    email: String? = null,
    rawPhone: String? = null,
    meta: Map<String, Any>? = null
)  {
    val userInfo: String
    private val fullName: String
        get() = listOfNotNull(firstName, lastName)
            .joinToString(" ")
            .capitalize()
    private val initials: String
        get() = listOfNotNull(firstName, lastName)
            .map { it.first().toUpperCase() }
            .joinToString(" ")

    private var phone: String? = null
        set(value){
            //field = value?.replace("[^+\\d]".toRegex(),"")
            value?:return
            val normal = value.replace("[^+\\d]".toRegex(),"")
          //  if(((normal.length == 12) && (normal[0] == '+') && (normal.count{it == '+'} == 1)).not())
         //   if(!((normal.length == 12) && (normal[0] == '+')))
          ////  var k:List<Char> = listOf(normal[2])
         //   for (i in 1..12) k =listOf(normal[i])
           // if(((normal.length == 12) && (normal[0] == '+') && (normal.count{it == '+'} == 1) && for ((i in 1..12) listOf(normal[i])).not()))
              //      throw IllegalArgumentException("Enter a valid phone number starting with a + and containing 11 digits")

            field = normal
        }

    private var _login:String? = null
    internal var login: String
        set(value){
            _login = value?.toLowerCase()
        }
        get() = _login!!

//    private val salt :String by lazy {
//        ByteArray(16).also { SecureRandom().nextBytes(it) }.toString()
//    }
    private var salt :String = ByteArray(16).also { SecureRandom().nextBytes(it) }.toString()
    private lateinit var passwordHash: String

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    var accessCode: String? = null

    //for email
    constructor(
        firstName: String,
        lastName: String?,
        email: String?,
        password:String
    ): this(firstName, lastName, email = email, meta= mapOf("auth" to "password")){
        println("Secondary mail constructor")
        passwordHash = encrypt(password)
    }

    //for phone
    constructor(
        firstName: String,
        lastName: String?,
        rawPhone:String
    ): this(firstName, lastName, rawPhone = rawPhone, meta= mapOf("auth" to "sms")){
        println("Secondary phone constructor")
//        val code = generateAccesCode()
//        passwordHash = encrypt(code)
//        accessCode = code
//        sendAccessCodeToUser(rawPhone, code)
        makeAccessCode()
    }

    constructor(
        firstName: String,
        lastName: String?,
        email: String?,
        _salt: String,
        hash:String,
        rawPhone: String?
    ): this(firstName, lastName, email = email, rawPhone = rawPhone, meta= mapOf("src" to "csv")){
        println("Secondary phone constructor")
        passwordHash = hash
        salt =_salt
        print("")
    }




    init{
        println("First init block, primary constructor was called")

        check(!firstName.isBlank()) { "FirstName must be not blank"}
        check(email.isNullOrBlank() || rawPhone.isNullOrBlank()) { "Email or phone must be not blank" }

        phone = rawPhone
        login = email ?: phone!!

        userInfo = """
            firstName: $firstName
            lastName: $lastName
            login: $login
            fullName: $fullName
            initials: $initials
            email: $email
            phone: $phone
            meta: $meta
    """.trimIndent()
    }

    fun checkPassword(pass:String) = encrypt(pass) == passwordHash
    fun checkSMS(pass:String) = pass == accessCode


    fun changePassword(oldPass:String, newPass:String){
        if(checkPassword(oldPass)) passwordHash = encrypt(newPass)
        else throw IllegalArgumentException("The entered password does not match the current password")
    }
    fun makeAccessCode():String{
        val code = generateAccesCode()
        passwordHash = encrypt(code)
        accessCode = code
        sendAccessCodeToUser(login, code)
        return code
    }
    private fun encrypt(password: String): String = salt.plus(password).md5()
    private fun encryptImport(salt: String,hash: String) = salt.plus(hash).md5()

    private fun generateAccesCode(): String{
        val possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"

        return StringBuilder().apply {
            repeat(6){
                (possible.indices).random().also { index ->
                    append(possible[index])
                }
            }
        }.toString()
    }

    private fun sendAccessCodeToUser(phone: String, code: String) {
        println("..... sending access code: $code on $phone")

    }

    private fun String.md5() : String{
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(toByteArray()) //16 byte
        val hexString = BigInteger(1, digest).toString(16)
        return hexString.padStart(32,'0')
    }

    companion object Factory {
        fun makeUser(
            fullname: String,
            email: String? = null,
            password: String? = null,
            phone: String? = null,
            hashSalt:String?=null
        ): User {
            val (firstName, lastName) = fullname.fullNameToPair()
            val (hash: String?,_salt:String?) = hashSalt?.HashSaltToPair() ?: null to null


            return when{
                !hashSalt.isNullOrBlank() -> User(firstName, lastName, email,hash!!,_salt!!, phone)
                !phone.isNullOrBlank() -> User(firstName, lastName, phone)
                !email.isNullOrBlank() && !password.isNullOrBlank() -> User(firstName, lastName, email, password)
                else -> throw IllegalArgumentException("Email or phone must be not null or black")
            }


        }


        private fun String.fullNameToPair() : Pair<String, String?>{
            return this.split(" ")
                .filter {it.isNotBlank()}
                .run {
                    when(size){
                        1 -> first() to null
                        2 -> first() to last()
                        else -> throw IllegalArgumentException("Fullname must contain only first and last name " +
                                "current split result ${this@fullNameToPair}")
                    }
                }
        }

        private fun String.HashSaltToPair() :Pair<String,String>{
            return this.split(":")
                .run{
                    first() to last()
                }

        }

    }

}





































