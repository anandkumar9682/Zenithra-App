package com.asuni.zenithra.di;

import android.content.Context;
import com.asuni.zenithra.database.AppDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DatabaseModule_ProvideAppDatabaseFactory implements Factory<AppDatabase> {
  private final Provider<Context> appContextProvider;

  public DatabaseModule_ProvideAppDatabaseFactory(Provider<Context> appContextProvider) {
    this.appContextProvider = appContextProvider;
  }

  @Override
  public AppDatabase get() {
    return provideAppDatabase(appContextProvider.get());
  }

  public static DatabaseModule_ProvideAppDatabaseFactory create(
      Provider<Context> appContextProvider) {
    return new DatabaseModule_ProvideAppDatabaseFactory(appContextProvider);
  }

  public static AppDatabase provideAppDatabase(Context appContext) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAppDatabase(appContext));
  }
}
