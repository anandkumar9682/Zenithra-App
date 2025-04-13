package com.asuni.zenithra.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

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
public final class NetworkModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final Provider<HeaderInterceptor> headerInterceptorProvider;

  private final Provider<NetworkInterceptor> networkInterceptorProvider;

  public NetworkModule_ProvideOkHttpClientFactory(
      Provider<HeaderInterceptor> headerInterceptorProvider,
      Provider<NetworkInterceptor> networkInterceptorProvider) {
    this.headerInterceptorProvider = headerInterceptorProvider;
    this.networkInterceptorProvider = networkInterceptorProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOkHttpClient(headerInterceptorProvider.get(), networkInterceptorProvider.get());
  }

  public static NetworkModule_ProvideOkHttpClientFactory create(
      Provider<HeaderInterceptor> headerInterceptorProvider,
      Provider<NetworkInterceptor> networkInterceptorProvider) {
    return new NetworkModule_ProvideOkHttpClientFactory(headerInterceptorProvider, networkInterceptorProvider);
  }

  public static OkHttpClient provideOkHttpClient(HeaderInterceptor headerInterceptor,
      NetworkInterceptor networkInterceptor) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOkHttpClient(headerInterceptor, networkInterceptor));
  }
}
