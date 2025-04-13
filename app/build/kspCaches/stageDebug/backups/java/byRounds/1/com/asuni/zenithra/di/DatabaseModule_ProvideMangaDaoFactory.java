package com.asuni.zenithra.di;

import com.asuni.zenithra.database.AppDatabase;
import com.asuni.zenithra.database.dao.MangaDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DatabaseModule_ProvideMangaDaoFactory implements Factory<MangaDao> {
  private final Provider<AppDatabase> appDatabaseProvider;

  public DatabaseModule_ProvideMangaDaoFactory(Provider<AppDatabase> appDatabaseProvider) {
    this.appDatabaseProvider = appDatabaseProvider;
  }

  @Override
  public MangaDao get() {
    return provideMangaDao(appDatabaseProvider.get());
  }

  public static DatabaseModule_ProvideMangaDaoFactory create(
      Provider<AppDatabase> appDatabaseProvider) {
    return new DatabaseModule_ProvideMangaDaoFactory(appDatabaseProvider);
  }

  public static MangaDao provideMangaDao(AppDatabase appDatabase) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideMangaDao(appDatabase));
  }
}
