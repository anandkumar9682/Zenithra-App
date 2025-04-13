package com.asuni.zenithra.di;

import android.content.Context;
import android.content.SharedPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class SharedPreferenceModule_ProvideSharedPreferencesFactory implements Factory<SharedPreferences> {
  private final Provider<Context> contextProvider;

  public SharedPreferenceModule_ProvideSharedPreferencesFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SharedPreferences get() {
    return provideSharedPreferences(contextProvider.get());
  }

  public static SharedPreferenceModule_ProvideSharedPreferencesFactory create(
      Provider<Context> contextProvider) {
    return new SharedPreferenceModule_ProvideSharedPreferencesFactory(contextProvider);
  }

  public static SharedPreferences provideSharedPreferences(Context context) {
    return Preconditions.checkNotNullFromProvides(SharedPreferenceModule.INSTANCE.provideSharedPreferences(context));
  }
}
