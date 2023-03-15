package uz.akhbor.githubtestapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import uz.akhbor.githubtestapp.service.UsersRepository
import uz.akhbor.githubtestapp.service.api.UsersApi

/**
 * Module Dagger responsible for api services
 *
 * @author Vitaliy Zarubin
 */
@Module
@InstallIn(ViewModelComponent::class)
object UsersNetwork {

    /**
     * HTTP API into a interface
     */
    @Provides
    fun provideUsersApi(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)

    /**
     * Users Service
     */
    @Provides
    fun provideUsersService(api: UsersApi): UsersRepository = UsersRepository(api)
}
