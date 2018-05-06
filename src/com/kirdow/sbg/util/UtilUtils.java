package com.kirdow.sbg.util;

public class UtilUtils {

    public interface FuncCallback<R> {
        R run();
    }

    public static <T> T saveCall(FuncCallback<T> callback, T _default) {
        try {
            return callback.run();
        } catch (Throwable throwable) {
            return _default;
        }
    }

}
