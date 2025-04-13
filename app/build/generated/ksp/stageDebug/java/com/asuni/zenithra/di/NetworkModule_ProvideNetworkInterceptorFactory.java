package com.asuni.zenithra.di;

import android.content.Context;
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
public final class NetworkModule_ProvideNetworkInterceptorFactory implements Factory<NetworkInterceptor> {
  private final Provider<Context> contextProvider;

  public NetworkModule_ProvideNetworkInterceptorFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public NetworkInterceptor get() {
    return provideNetworkInterceptor(contextProvider.get());
  }

  public static NetworkModule_ProvideNetworkInterceptorFactory create(
      Provider<Context> contextProvider) {
    return new NetworkModule_ProvideNetworkInterceptorFactory(contextProvider);
  }

  public static NetworkInterceptor provideNetworkInterceptor(Context context) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideNetworkInterceptor(context));
  }
}
