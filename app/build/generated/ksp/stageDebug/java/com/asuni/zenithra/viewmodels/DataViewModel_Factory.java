package com.asuni.zenithra.viewmodels;

import com.asuni.zenithra.repository.DataRepository;
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
public final class DataViewModel_Factory implements Factory<DataViewModel> {
  private final Provider<DataRepository> dataRepositoryProvider;

  public DataViewModel_Factory(Provider<DataRepository> dataRepositoryProvider) {
    this.dataRepositoryProvider = dataRepositoryProvider;
  }

  @Override
  public DataViewModel get() {
    return newInstance(dataRepositoryProvider.get());
  }

  public static DataViewModel_Factory create(Provider<DataRepository> dataRepositoryProvider) {
    return new DataViewModel_Factory(dataRepositoryProvider);
  }

  public static DataViewModel newInstance(DataRepository dataRepository) {
    return new DataViewModel(dataRepository);
  }
}
