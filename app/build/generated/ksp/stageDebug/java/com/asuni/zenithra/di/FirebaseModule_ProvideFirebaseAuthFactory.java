package com.asuni.zenithra.di;

import com.google.firebase.auth.FirebaseAuth;
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
public final class FirebaseModule_ProvideFirebaseAuthFactory implements Factory<FirebaseAuth> {
  private final FirebaseModule module;

  public FirebaseModule_ProvideFirebaseAuthFactory(FirebaseModule module) {
    this.module = module;
  }

  @Override
  public FirebaseAuth get() {
    return provideFirebaseAuth(module);
  }

  public static FirebaseModule_ProvideFirebaseAuthFactory create(FirebaseModule module) {
    return new FirebaseModule_ProvideFirebaseAuthFactory(module);
  }

  public static FirebaseAuth provideFirebaseAuth(FirebaseModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideFirebaseAuth());
  }
}
