package pl.matiu.kalistenika.firebaseAuth

import android.util.Log


//state
interface UserState {
    fun signUp(user: User, registerContext: RegisterContext)
    fun signIn(user: User, loginContext: LoginContext)
    fun logOut(user: User, loginContext: LoginContext)
}

//context
data class User(var email: String,
                var password: String,
                var state: UserState = Anonymous)

//concrete state
object Anonymous : UserState {
    override fun signUp(user: User, registerContext: RegisterContext) {
        Log.d("Rejestracja", registerContext.register())
        user.state = Logged
    }

    override fun signIn(user: User,  loginContext: LoginContext) {
        Log.d("logowanie", loginContext.login())
        user.state = Logged
    }

    override fun logOut(user: User, loginContext: LoginContext) {
        Log.d("wylogowanie", "najpierw musisz sie zalogowac")
    }

}

object Logged : UserState {
    override fun signUp(user: User, registerContext: RegisterContext) {
        Log.d("Rejestracja", "Jestes juz zarejestrowany")
    }

    override fun signIn(user: User, loginContext: LoginContext) {
        Log.d("logowanie", "jestes juz zalogowany")
    }

    override fun logOut(user: User, loginContext: LoginContext) {
        Log.d("wylogowanie", loginContext.logout())
        user.state = Anonymous
    }
}