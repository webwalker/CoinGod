-optimizationpasses 5
#表示混淆时不使用大小写混合类名
-dontusemixedcaseclassnames
#表示不跳过library中的非public的类
-dontskipnonpubliclibraryclasses
#打印混淆的详细信息
-verbose
# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
-dontshrink
-dontoptimize
-allowaccessmodification
-dontwarn
##表示不进行校验,这个校验作用 在java平台上的
#-dontpreverify
# Note that if you want to enable optimization, you cannot just
# include optimization flags in your own project configuration file;
# instead you will need to point to the
# "proguard-android-optimize.txt" file instead of this one from your
# project.properties file.
-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * { native <methods>; }
# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View { void set*(***); *** get*(); }
# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity { public void *(android.view.View); }
# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * { public static **[] values(); public static ** valueOf(java.lang.String); }
-keepclassmembers class * implements android.os.Parcelable { public static final android.os.Parcelable$Creator CREATOR; }
-keepclassmembers class **.R$* { public static <fields>; }
# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version. We know about them, and they are safe.
-dontwarn android.support.**
# Understand the @Keep support annotation.
-keep class android.support.annotation.Keep
-keep @android.support.annotation.Keep class * {*;}
-keepclasseswithmembers class * { @android.support.annotation.Keep <methods>; }
-keepclasseswithmembers class * { @android.support.annotation.Keep <fields>; }
-keepclasseswithmembers class * { @android.support.annotation.Keep <init>(...); }

#-dontobfuscate
#-dontshrink
-ignorewarning

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable

#--------------System---------------
-keepattributes SourceFile,LineNumberTable
-keepattributes Exceptions, Signature, InnerClasses
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-keepattributes JavascriptInterface
-keepattributes Deprecated, EnclosingMethod #避免混淆泛型 如果混淆报错建议关掉

-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn javax.annotation.**

#okhttp3
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-dontwarn okio.**
-keep class okio.**{*;}

-dontwarn redis.clients.**
-keep class redis.clients.** {*;}

-dontwarn org.**
-keep class org.** {*;}

-dontwarn com.webwalker.http.**
-keep class com.webwalker.http.** {*;}
-keep class com.webwalker.http.**$* {
    *;
}
-keep class com.webwalker.main.** {*;}

-dontwarn com.google.**
-keep class com.google.** { *; }

## jifen keepalive
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keepattributes Signature
-keep class * implements java.io.Serializable { *; }
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-dontwarn okio.**
#小米
-keep public class * extends android.content.BroadcastReceiver
#个推
-keep class org.json.** { *; }
#信鸽
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver

-keep class org.greenrobot.** { *; }
-keep class com.google.** { *; }
-keep class org.hamcrest.** { *; }
-dontwarn javax.**
-keep class javax.** { *; }
-dontwarn com.sun.**
-keep class com.sun.activation.** { *; }


