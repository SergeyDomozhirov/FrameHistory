package ru.lehandr.framehistoryrussia.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.lehandr.domain.repository.FireBaseRepository
import ru.lehandr.domain.useCase.EpochsListUseCase
import ru.lehandr.framehistoryrussia.data.FireBaseRepositoryImpl
import ru.lehandr.domain.setting.env.Environment

@Module
@InstallIn(ViewModelComponent::class)
class FirebaseModule {

    @Provides
    fun provideDB() : FirebaseFirestore {
        val db = Firebase.firestore
        val settings = firestoreSettings {
            isPersistenceEnabled = false
        }
        db.firestoreSettings = settings
        return db
    }

    @Provides
    fun provideFireBaseRepository(db: FirebaseFirestore, env: Environment.Companion) : FireBaseRepository {
        return FireBaseRepositoryImpl(db, env)
    }

    @Provides
    fun provideEpochsListUseCase(repository: FireBaseRepository) : EpochsListUseCase {
       return EpochsListUseCase(repository)
    }

    @Provides
    fun provideEnvironment() : Environment.Companion {
        return Environment
    }

    //Todo Добавить провайдер FirebaseStorage (Firebase.storage)
    //Todo Добавить провайдер на EpochLoadImageUseCase

}