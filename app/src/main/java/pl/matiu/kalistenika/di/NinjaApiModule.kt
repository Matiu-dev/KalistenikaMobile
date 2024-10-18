package pl.matiu.kalistenika.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.matiu.kalistenika.exerciseApi.NinjaApi
import pl.matiu.kalistenika.exerciseApi.NinjaApiRepository
import pl.matiu.kalistenika.exerciseApi.NinjaApiRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NinjaApiModule {

    @Provides
    @Singleton
    fun provideNinjaApi(): NinjaApi {
        return Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(NinjaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNinjaApiRepository(ninjaApi: NinjaApi): NinjaApiRepository {
        return NinjaApiRepositoryImpl(ninjaApi)
    }
}