package com.example.notessaver.di

import com.example.notessaver.api.AuthInterceptor
import com.example.notessaver.api.NotesApi
import com.example.notessaver.api.UserApi
import com.example.notessaver.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofitBuilder: Retrofit.Builder): UserApi {
        return retrofitBuilder.build().create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun providesNoteAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): NotesApi {
        return retrofitBuilder.client(okHttpClient).build().create(NotesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)  // max time to establish a connection
            .readTimeout(30, TimeUnit.SECONDS)     // max time to wait for server response
            .writeTimeout(30, TimeUnit.SECONDS)    // max time to send request body
            .build()
    }

//    @Module
//    @InstallIn(SingletonComponent::class)
//    object DatabaseModule {
//        @Provides
//        @Singleton
//        fun provideDatabase(@ApplicationContext context: Context): NotesDatabase {
//            return Room.databaseBuilder(
//                context,
//                NotesDatabase::class.java,
//                "notes_database"
//            ).build()
//        }
//
//        @Provides
//        fun provideNotesDao(database: NotesDatabase): NotesDao {
//            return database.notesDao()
//        }
//    }



}