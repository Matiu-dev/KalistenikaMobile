package pl.matiu.kalistenika.firebaseAuth


interface LoginStrategy {
    fun login(): String
    fun logout(): String
}

class GoogleLogin: LoginStrategy {
    override fun login(): String {
        return "Zalogowano za pomoca google"
    }

    override fun logout(): String {
        return "Wylogowano za pomoca google"
    }
}

class GithubLogin: LoginStrategy {
    override fun login(): String {
        return "Zalogowano za pomoca github"
    }

    override fun logout(): String {
        return "Wylogowano za pomoca github"
    }
}

class LoginContext(private var strategy: LoginStrategy) {
    fun setStrategy(strategy: LoginStrategy) {
        this.strategy = strategy
    }

    fun login(): String {
        return strategy.login()
    }

    fun logout(): String {
        return strategy.logout()
    }
}