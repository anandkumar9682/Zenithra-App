package com.asuni.zenithra.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class NetworkModule_ProvideHeaderInterceptorFactory implements Factory<HeaderInterceptor> {
  @Override
  public HeaderInterceptor get() {
    return provideHeaderInterceptor();
  }

  public static NetworkModule_ProvideHeaderInterceptorFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static HeaderInterceptor provideHeaderInterceptor() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideHeaderInterceptor());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_ProvideHeaderInterceptorFactory INSTANCE = new NetworkModule_ProvideHeaderInterceptorFactory();
  }
}
