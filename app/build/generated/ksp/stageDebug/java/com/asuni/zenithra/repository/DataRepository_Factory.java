package com.asuni.zenithra.repository;

import com.asuni.zenithra.database.AppDatabase;
import com.asuni.zenithra.network.NetworkApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class DataRepository_Factory implements Factory<DataRepository> {
  private final Provider<NetworkApi> apiServiceProvider;

  private final Provider<AppDatabase> appDatabaseProvider;

  public DataRepository_Factory(Provider<NetworkApi> apiServiceProvider,
      Provider<AppDatabase> appDatabaseProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.appDatabaseProvider = appDatabaseProvider;
  }

  @Override
  public DataRepository get() {
    return newInstance(apiServiceProvider.get(), appDatabaseProvider.get());
  }

  public static DataRepository_Factory create(Provider<NetworkApi> apiServiceProvider,
      Provider<AppDatabase> appDatabaseProvider) {
    return new DataRepository_Factory(apiServiceProvider, appDatabaseProvider);
  }

  public static DataRepository newInstance(NetworkApi apiService, AppDatabase appDatabase) {
    return new DataRepository(apiService, appDatabase);
  }
}
