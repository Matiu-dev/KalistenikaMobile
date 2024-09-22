package pl.matiu.kalistenika.logger

import android.util.Log
import java.util.*

interface Logger {//Component
    fun log(message: String)
}
class ConsoleLogger: Logger {//Concrete component
    override fun log(message: String) {
        Log.d("Message",message)
    }
}

abstract class LoggerDecorator(protected val logger: Logger): Logger//Decorator

//concrete decorators
class UniqueIdLogger(logger: Logger): LoggerDecorator(logger) {
    override fun log(message: String) = logger.log("${UUID.randomUUID()} $message")
}

class ThreadIdLogger(logger: Logger): LoggerDecorator(logger) {
    override fun log(message: String) = logger.log("$message (Thread: ${Thread.currentThread().name})")
}

class DateTimeLogger(logger: Logger, private val currentDate: Date = Date()): LoggerDecorator(logger) {
    override fun log(message: String) = logger.log("$message (CurrentDate: ${currentDate})")
}