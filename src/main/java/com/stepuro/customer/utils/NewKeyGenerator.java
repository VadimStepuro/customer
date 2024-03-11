package com.stepuro.customer.utils;

import org.springframework.cache.interceptor.KeyGenerator;
import reactor.util.annotation.NonNull;

import java.lang.reflect.Method;

public class NewKeyGenerator implements KeyGenerator {
    @Override
    @NonNull
    public Object generate(Object target, @NonNull Method method, @NonNull Object... params) {
        return target.hashCode();
    }
}
