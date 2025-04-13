package com.asuni.zenithra.util.helper;

import android.content.Context;
import com.asuni.zenithra.database.dao.UsersDao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
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
public final class FirebaseHelper_Factory implements Factory<FirebaseHelper> {
  private final Provider<DatabaseReference> firebaseDbRefProvider;

  private final Provider<UsersDao> usersDaoProvider;

  private final Provider<Context> contextProvider;

  private final Provider<FirebaseAuth> authProvider;

  public FirebaseHelper_Factory(Provider<DatabaseReference> firebaseDbRefProvider,
      Provider<UsersDao> usersDaoProvider, Provider<Context> contextProvider,
      Provider<FirebaseAuth> authProvider) {
    this.firebaseDbRefProvider = firebaseDbRefProvider;
    this.usersDaoProvider = usersDaoProvider;
    this.contextProvider = contextProvider;
    this.authProvider = authProvider;
  }

  @Override
  public FirebaseHelper get() {
    return newInstance(firebaseDbRefProvider.get(), usersDaoProvider.get(), contextProvider.get(), authProvider.get());
  }

  public static FirebaseHelper_Factory create(Provider<DatabaseReference> firebaseDbRefProvider,
      Provider<UsersDao> usersDaoProvider, Provider<Context> contextProvider,
      Provider<FirebaseAuth> authProvider) {
    return new FirebaseHelper_Factory(firebaseDbRefProvider, usersDaoProvider, contextProvider, authProvider);
  }

  public static FirebaseHelper newInstance(DatabaseReference firebaseDbRef, UsersDao usersDao,
      Context context, FirebaseAuth auth) {
    return new FirebaseHelper(firebaseDbRef, usersDao, context, auth);
  }
}
