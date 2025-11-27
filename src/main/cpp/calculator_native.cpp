#include <jni.h>
#include <stdio.h>

extern "C" {

    JNIEXPORT jdouble JNICALL Java_com_mycalculator_app_Calculator_addNative(JNIEnv *env, jobject obj, jdouble a, jdouble b) {
        return a + b;
    }

    JNIEXPORT jdouble JNICALL Java_com_mycalculator_app_Calculator_subtractNative(JNIEnv *env, jobject obj, jdouble a, jdouble b) {
        return a - b;
    }

    JNIEXPORT jdouble JNICALL Java_com_mycalculator_app_Calculator_multiplyNative(JNIEnv *env, jobject obj, jdouble a, jdouble b) {
        return a * b;
    }

    JNIEXPORT jdouble JNICALL Java_com_mycalculator_app_Calculator_divideNative(JNIEnv *env, jobject obj, jdouble a, jdouble b) {
        if (b == 0) {
            jclass exClass = env->FindClass("java/lang/IllegalArgumentException");
            env->ThrowNew(exClass, "No se puede dividir por cero (Desde C++)");
            return 0;
        }
        return a / b;
    }

}
