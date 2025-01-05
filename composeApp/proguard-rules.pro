-keep class kotlinx.serialization.** { *; }
-keepattributes *Annotation*
-keepclassmembers class ** {
    @kotlinx.serialization.Serializable <fields>;
}