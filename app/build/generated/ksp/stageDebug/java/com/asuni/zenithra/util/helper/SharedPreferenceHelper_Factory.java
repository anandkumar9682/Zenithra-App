package com.asuni.zenithra.util.helper;

import android.content.SharedPreferences;
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
public final class SharedPreferenceHelper_Factory implements Factory<SharedPreferenceHelper> {
  private final Provider<SharedPreferences> sharedPrefProvider;

  public SharedPreferenceHelper_Factory(Provider<SharedPreferences> sharedPrefProvider) {
    this.sharedPrefProvider = sharedPrefProvider;
  }

  @Override
  public SharedPreferenceHelper get() {
    return newInstance(sharedPrefProvider.get());
  }

  public static SharedPreferenceHelper_Factory create(
      Provider<SharedPreferences> sharedPrefProvider) {
    return new SharedPreferenceHelper_Factory(sharedPrefProvider);
  }

  public static SharedPreferenceHelper newInstance(SharedPreferences sharedPref) {
    return new SharedPreferenceHelper(sharedPref);
  }
}
