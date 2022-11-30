package ru.lehandr.framehistoryrussia.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import ru.lehandr.domain.repository.FireBaseRepository
import ru.lehandr.domain.repository.FirebaseStorageRepository
import ru.lehandr.domain.setting.env.Environment
import ru.lehandr.domain.useCase.ComicLoadImageUseCase
import ru.lehandr.domain.useCase.ComicsListUseCase
import ru.lehandr.domain.useCase.EpochLoadImageUseCase
import ru.lehandr.domain.useCase.EpochsListUseCase
import ru.lehandr.framehistoryrussia.data.FireBaseRepositoryImpl
import ru.lehandr.framehistoryrussia.data.FirebaseStorageRepositoryImpl
import ru.lehandr.framehistoryrussia.data.firebase.firestore.Firestore
import ru.lehandr.framehistoryrussia.data.firebase.firestore.FirestoreImpl
import ru.lehandr.framehistoryrussia.data.firebase.storage.FireStorage
import ru.lehandr.framehistoryrussia.data.firebase.storage.FireStorageImpl
// Создаем модуль и Hilt
@Module
@InstallIn(ViewModelComponent::class, FragmentComponent::class)
class FirebaseModule {

    @Provides
    // TODO Как правильно описать работу в этом классе
    //Создаем provideDB внутри пусто и возвращаем
    fun provideDB(): FirebaseFirestore {
        val db = Firebase.firestore
        val settings = firestoreSettings {
            isPersistenceEnabled = false
        }
        db.firestoreSettings = settings
        return db
    }

    @Provides
    fun provideFirebaseStorage(): FirebaseStorage {
        return Firebase.storage
    }

    @Provides
    // Создаем provideFireBaseRepository на входе у нас db: Firestore-интерфейс и возвращаем FireBaseRepository внутри обращаемся к FireBaseRepositoryImpl
    fun provideFireBaseRepository(db: Firestore): FireBaseRepository {
        return FireBaseRepositoryImpl(db)
    }

    @Provides
    fun provideFirebaseStorageRepository(storage: FireStorage): FirebaseStorageRepository {
        return FirebaseStorageRepositoryImpl(storage)
    }

    @Provides
    fun provideFirestore(db: FirebaseFirestore, env: Environment.Companion): Firestore {
        return FirestoreImpl(db, env)
    }

    @Provides
    fun provideFireStorage(storage: FirebaseStorage): FireStorage {
        return FireStorageImpl(storage)
    }

    @Provides
    fun provideEpochsListUseCase(repository: FireBaseRepository): EpochsListUseCase {
        return EpochsListUseCase(repository)
    }

    @Provides
    fun provideEpochLoadImageUseCase(repository: FirebaseStorageRepository): EpochLoadImageUseCase {
        return EpochLoadImageUseCase(repository)
    }

//    @Provides
//    fun provideComicsListUseCase(repository: FireBaseRepository): ComicsListUseCase {
//        return ComicsListUseCase(repository)
//    }
//
//    @Provides
//    fun provideComicLoadImageUseCase(repository: FirebaseStorageRepository): ComicLoadImageUseCase {
//        return ComicLoadImageUseCase(repository)
//   }

    @Provides
    fun provideEnvironment(): Environment.Companion {
        return Environment
    }

}