package pl.matiu.kalistenika.internalStorage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.matiu.kalistenika.trainingModel.TrainingModel
import java.io.File

//https://developer.android.com/reference/android/content/Context#getExternalFilesDir(java.lang.String)
class TrainingInternalStorageService {

    //TODO do otestowania
    val TRENINGS_FILE_NAME: String = "TreningList"

    fun saveTrainingToInternalStorage(
        context: Context,
        trainingName: String
    ) {

        var file = File(context.getExternalFilesDir(null),
            TRENINGS_FILE_NAME)

        val gson = Gson()
        val itemType = object : TypeToken<List<TrainingModel>>() {}.type

        var trainingList: MutableList<TrainingModel> = if (file.exists()) {
            val fileContent = file.readText()
            gson.fromJson(fileContent, itemType) ?: mutableListOf()
        } else {
            mutableListOf()
        }

        trainingList.add(TrainingModel(trainingList.size, trainingName))

        file.writeText(gson.toJson(trainingList))
    }

    fun getTrainingNameById(context: Context, trainingId: Int?): String {
        var file = File(context.getExternalFilesDir(null),
            TRENINGS_FILE_NAME)

        val gson = Gson()
        val itemType = object : TypeToken<List<TrainingModel>>() {}.type

        var trainingList: MutableList<TrainingModel> = if (file.exists()) {
            val fileContent = file.readText()
            gson.fromJson(fileContent, itemType) ?: mutableListOf()
        } else {
            mutableListOf()
        }

        return trainingList.find { it.trainingId == trainingId }?.name ?: "none"
    }

    fun loadTrainingToInternalStorage(context: Context): List<TrainingModel> {

        var file: File = File(context.getExternalFilesDir(null),
            TRENINGS_FILE_NAME)

        val gson = Gson()
        val itemType = object : TypeToken<List<TrainingModel>>() {}.type

        return if(file.exists()) {
            val fileContent = file.readText()
            gson.fromJson(fileContent, itemType) ?: emptyList()
        } else {
            emptyList()
        }

//        return gson.fromJson<MutableList<TrainingModel>>(file.toString(), itemType)
    }

    //TODO usuniecie treningu powinno usunac rowniez cwiczenia
    fun deleteTrainingFromInternalStorage(context: Context, trainingId: Int) {
        var file = File(context.getExternalFilesDir(null),
            TRENINGS_FILE_NAME)

        val gson = Gson()
        val itemType = object : TypeToken<List<TrainingModel>>() {}.type

        var trainingList: MutableList<TrainingModel> = if (file.exists()) {
            val fileContent = file.readText()
            gson.fromJson(fileContent, itemType) ?: mutableListOf()
        } else {
            mutableListOf()
        }

        trainingList.removeAt(trainingId)

        file.writeText(gson.toJson(trainingList))
    }

    fun getTrainingById(context: Context, trainingId: Int): String {
        var file = File(context.getExternalFilesDir(null),
            TRENINGS_FILE_NAME)

        val gson = Gson()
        val itemType = object : TypeToken<List<TrainingModel>>() {}.type

        var trainingList: MutableList<TrainingModel> = if (file.exists()) {
            val fileContent = file.readText()
            gson.fromJson(fileContent, itemType) ?: mutableListOf()
        } else {
            mutableListOf()
        }

        return trainingList.find { it.trainingId == trainingId }?.name ?: ""
    }

    //TODO dodanie funkcji modyfikacji treningu
}