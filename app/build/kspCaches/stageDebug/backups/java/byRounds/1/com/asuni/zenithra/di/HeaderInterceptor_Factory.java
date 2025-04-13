package com.asuni.zenithra.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class HeaderInterceptor_Factory implements Factory<HeaderInterceptor> {
  @Override
  public HeaderInterceptor get() {
    return newInstance();
  }

  public static HeaderInterceptor_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static HeaderInterceptor newInstance() {
    return new HeaderInterceptor();
  }

  private static final class InstanceHolder {
    private static final HeaderInterceptor_Factory INSTANCE = new HeaderInterceptor_Factory();
  }
}
