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
public final class FirebaseModule_Factory implements Factory<FirebaseModule> {
  @Override
  public FirebaseModule get() {
    return newInstance();
  }

  public static FirebaseModule_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirebaseModule newInstance() {
    return new FirebaseModule();
  }

  private static final class InstanceHolder {
    private static final FirebaseModule_Factory INSTANCE = new FirebaseModule_Factory();
  }
}
