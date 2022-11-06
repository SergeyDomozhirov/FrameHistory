package ru.lehandr.framehistoryrussia.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.lehandr.domain.repository.FireBaseRepository
import ru.lehandr.domain.useCase.EpochsListUseCase
import ru.lehandr.framehistoryrussia.data.FireBaseRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
class FirebaseModule {

    @Provides
    fun provideDB() : FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    fun provideFireBaseRepository(db: FirebaseFirestore) : FireBaseRepository {
        return FireBaseRepositoryImpl(db)
    }

    @Provides
    fun provideEpochsListUseCase(repository: FireBaseRepository) : EpochsListUseCase {
       return EpochsListUseCase(repository)
    }


}