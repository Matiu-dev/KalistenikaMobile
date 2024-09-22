package pl.matiu.kalistenika.firebaseAuth

interface RegisterStrategy {
    fun register(): String
}

class GoogleRegister: RegisterStrategy {
    override fun register(): String {
        return "Zarejestrowano za pomoca google"
    }
}

class GithubRegister: RegisterStrategy {
    override fun register(): String {
        return "Zarejestrowano za pomoca github"
    }
}

class RegisterContext(private var strategy: RegisterStrategy) {
    fun setStrategy(strategy: RegisterStrategy) {
        this.strategy = strategy
    }

    fun register(): String {
        return strategy.register()
    }
}