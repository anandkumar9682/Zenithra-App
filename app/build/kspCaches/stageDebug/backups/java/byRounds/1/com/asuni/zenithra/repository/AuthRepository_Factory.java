package com.asuni.zenithra.repository;

import com.asuni.zenithra.database.AppDatabase;
import com.asuni.zenithra.network.NetworkApi;
import com.asuni.zenithra.util.helper.FirebaseHelper;
import com.asuni.zenithra.util.helper.SharedPreferenceHelper;
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<NetworkApi> apiServiceProvider;

  private final Provider<AppDatabase> appDatabaseProvider;

  private final Provider<FirebaseHelper> firebaseHelperProvider;

  private final Provider<SharedPreferenceHelper> sharedPreferenceHelperProvider;

  public AuthRepository_Factory(Provider<NetworkApi> apiServiceProvider,
      Provider<AppDatabase> appDatabaseProvider, Provider<FirebaseHelper> firebaseHelperProvider,
      Provider<SharedPreferenceHelper> sharedPreferenceHelperProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.appDatabaseProvider = appDatabaseProvider;
    this.firebaseHelperProvider = firebaseHelperProvider;
    this.sharedPreferenceHelperProvider = sharedPreferenceHelperProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(apiServiceProvider.get(), appDatabaseProvider.get(), firebaseHelperProvider.get(), sharedPreferenceHelperProvider.get());
  }

  public static AuthRepository_Factory create(Provider<NetworkApi> apiServiceProvider,
      Provider<AppDatabase> appDatabaseProvider, Provider<FirebaseHelper> firebaseHelperProvider,
      Provider<SharedPreferenceHelper> sharedPreferenceHelperProvider) {
    return new AuthRepository_Factory(apiServiceProvider, appDatabaseProvider, firebaseHelperProvider, sharedPreferenceHelperProvider);
  }

  public static AuthRepository newInstance(NetworkApi apiService, AppDatabase appDatabase,
      FirebaseHelper firebaseHelper, SharedPreferenceHelper sharedPreferenceHelper) {
    return new AuthRepository(apiService, appDatabase, firebaseHelper, sharedPreferenceHelper);
  }
}
