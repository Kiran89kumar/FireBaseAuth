# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\android-studio-sdk\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:


# others start
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes *Annotation*
-keepclasseswithmembers class * {
    native <methods>;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-dontwarn oauth.signpost.**
-dontwarn android.support.v4.**
-dontwarn android.support.v13.**
-dontwarn com.google.code.**
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}
# others end


# android start
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment
-keep class com.android.vending.billing.**
-keeppackagenames com.google.**
-keeppackagenames com.facebook.**
-keeppackagenames android.support.**
-keep class sun.misc.Unsafe { *; }
-keepattributes Signature
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }
-keep class com.facebook.** { *; }
-keep class com.google.gdata.util.** { *; }
-keep class oauth.signpost.** { *; }
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**
# android end


# okhttp start
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
# okhttp end


# serialization start
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
# serialization end


# parcelization start
-keepclassmembers class * implements android.os.Parcelable {
      public static final android.os.Parcelable$Creator *;
   }
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
   }

-keep class * implements android.os.Parcelable {
     public static android.os.Parcelable$Creator CREATOR;
   }

# Keep SafeParcelable value, needed for reflection. This is required to support backwards
# compatibility of some classes.
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
   }
# parcelization end


# gson start
-keep class com.google.gson.** { *; }
# gson end

# logs start

#logs end


# retrofit start
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
# retrofit end


# okhttp3 start
-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.*
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontnote okhttp3.*
# okhttp3 end


# okio start
-dontwarn okio.**
-dontnote okio.**
# okio end


# rxjava start
-dontwarn sun.misc.Unsafe
-dontwarn org.w3c.dom.bootstrap.DOMImplementationRegistry
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
# rxjava end

# dagger start
-dontwarn dagger.internal.codegen.**
-keepclassmembers,allowobfuscation class * {
    @javax.inject.* *;
    @dagger.* *;
    <init>();
}
-keep class dagger.* { *; }
-keep class javax.inject.* { *; }
-keep class * extends dagger.internal.Binding
-keep class * extends dagger.internal.ModuleAdapter
-keep class * extends dagger.internal.StaticInjection
# dagger end

-dontwarn com.google.errorprone.annotations.**

# model class start
-keep class com.fireauth.demo.fireauth1.data.model.**{*;}
# model class end

-dontnote org.apache.http.conn.scheme.HostNameResolver
-dontnote org.apache.http.conn.**
-dontnote org.apache.http.params.**
-dontnote android.net.http.**
-dontnote io.reactivex.**
-dontnote com.twitter.sdk.**
-dontnote okhttp3.internal.platform.**
-dontnote com.crashlytics.android.answers.**
-dontwarn dagger.android.AndroidInjector.**

-dontnote **ILicensingService
-dontnote com.android.vending.billing.IInAppBillingService

# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

-keepattributes Signature
-keepattributes *Annotation*