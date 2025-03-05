package pl.matiu.kalistenika.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.matiu.kalistenika.sharedPrefs.SharedPrefsRepository
import pl.matiu.kalistenika.sharedPrefs.SharedPrefsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPrefsModule {

    @Provides
    @Singleton
    fun getSharedPrefs(): SharedPrefsRepository {
        return SharedPrefsRepositoryImpl()
    }
}