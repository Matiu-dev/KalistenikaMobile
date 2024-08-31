package pl.matiu.kalistenika

import com.google.gson.Gson
import org.junit.Before
import org.junit.runner.manipulation.Ordering.Context
import org.mockito.Mockito.mock
import java.io.File

class RepetitionExerciseTest {

    private lateinit var context: Context
    private lateinit var file: File
    private val gson = Gson()

    @Before
    fun setUp() {
        context = mock()
        file = mock()



//        whenever(context.getExternalFilesDir)
    }
}