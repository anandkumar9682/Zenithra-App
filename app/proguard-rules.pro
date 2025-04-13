# ===================================
# General Rules
# ===================================
-keep public class com.asuni.zenithra.** { *; }
-keepattributes Signature, *Annotation*, RuntimeVisibleAnnotations, EnclosingMethod, InnerClasses
-keepclassmembers class com.asuni.zenithra.** { public <init>(...); }
-ignorewarnings
-optimizations !code/simplification/arithmetic
-allowaccessmodification

# ===================================
# Package-specific Rules
# ===================================
-keep class com.asuni.zenithra.database.** { *; }
-keep class com.asuni.zenithra.di.** { *; }
-keep class dagger.hilt.** { *; }
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-dontwarn dagger.hilt.**
-keep class com.asuni.zenithra.domain.** { *; }
-keep class com.asuni.zenithra.network.** { *; }
-keep interface retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keepclassmembers class * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep class com.asuni.zenithra.ui.** { *; }
-keepclassmembers class com.asuni.zenithra.ui.authentication.models.** { *; }
-keep class com.asuni.zenithra.util.** { *; }

# ===================================
# Third-Party Libraries
# ===================================
-keep class com.google.gson.** { *; }
-keepclassmembers class * { @com.google.gson.annotations.SerializedName <fields>; @com.google.gson.annotations.Expose <fields>; }
-keep class com.google.gson.TypeAdapterFactory { *; }
-keep class com.google.gson.TypeAdapter { *; }
-keep class com.google.gson.stream.** { *; }
-keep class androidx.room.** { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }
-keep class * extends androidx.room.RoomDatabase { public static final androidx.room.RoomDatabase$Callback *; }
-keep class kotlin.Metadata { *; }
-keep class kotlinx.coroutines.flow.** { *; }
-keep class kotlinx.coroutines.Deferred { *; }
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**
-keep class androidx.navigation.** { *; }
-dontwarn androidx.navigation.**
-keep class com.google.mlkit.vision.barcode.** { *; }
-dontwarn com.google.mlkit.**
-dontwarn timber.log.**
-keep class coil.** { *; }
-dontwarn coil.**
-keep class androidx.vectordrawable.** { *; }
-dontwarn androidx.vectordrawable.**
-keep class com.squareup.leakcanary.** { *; }
-dontwarn com.squareup.leakcanary.**

# ===================================
# Application Entry Points
# ===================================
-keep class **.MainActivity { *; }
-keep class **.Application { *; }
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keepclassmembers class * extends androidx.lifecycle.ViewModel { <init>(...); }
