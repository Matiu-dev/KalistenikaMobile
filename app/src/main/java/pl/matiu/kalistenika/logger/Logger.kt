package pl.matiu.kalistenika.logger

import android.util.Log
import java.util.*

interface Logger {//Component
    fun log(title: String, message: String)
}
class ConsoleLogger: Logger {//Concrete component
    override fun log(title: String, message: String) {
        Log.d(title, message)
    }
}

abstract class LoggerDecorator(protected val logger: Logger): Logger//Decorator

//concrete decorators
class UniqueIdLogger(logger: Logger): LoggerDecorator(logger) {
    override fun log(title: String, message: String) = logger.log(title,"${UUID.randomUUID()} $message")
}

class ThreadIdLogger(logger: Logger): LoggerDecorator(logger) {
    override fun log(title: String, message: String) = logger.log(title,"$message (Thread: ${Thread.currentThread().name})")
}

class DateTimeLogger(logger: Logger, private val currentDate: Date = Date()): LoggerDecorator(logger) {
    override fun log(title: String, message: String) = logger.log(title, "$message (CurrentDate: ${currentDate})")
}