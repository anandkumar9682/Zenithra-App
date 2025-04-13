package com.asuni.zenithra.di;

import com.asuni.zenithra.database.AppDatabase;
import com.asuni.zenithra.database.dao.UsersDao;
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
public final class DatabaseModule_ProvideUsersDaoFactory implements Factory<UsersDao> {
  private final Provider<AppDatabase> appDatabaseProvider;

  public DatabaseModule_ProvideUsersDaoFactory(Provider<AppDatabase> appDatabaseProvider) {
    this.appDatabaseProvider = appDatabaseProvider;
  }

  @Override
  public UsersDao get() {
    return provideUsersDao(appDatabaseProvider.get());
  }

  public static DatabaseModule_ProvideUsersDaoFactory create(
      Provider<AppDatabase> appDatabaseProvider) {
    return new DatabaseModule_ProvideUsersDaoFactory(appDatabaseProvider);
  }

  public static UsersDao provideUsersDao(AppDatabase appDatabase) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideUsersDao(appDatabase));
  }
}
