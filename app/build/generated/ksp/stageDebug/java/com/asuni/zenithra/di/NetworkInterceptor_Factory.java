package com.asuni.zenithra.di;

import android.content.Context;
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
public final class NetworkInterceptor_Factory implements Factory<NetworkInterceptor> {
  private final Provider<Context> contextProvider;

  public NetworkInterceptor_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public NetworkInterceptor get() {
    return newInstance(contextProvider.get());
  }

  public static NetworkInterceptor_Factory create(Provider<Context> contextProvider) {
    return new NetworkInterceptor_Factory(contextProvider);
  }

  public static NetworkInterceptor newInstance(Context context) {
    return new NetworkInterceptor(context);
  }
}
