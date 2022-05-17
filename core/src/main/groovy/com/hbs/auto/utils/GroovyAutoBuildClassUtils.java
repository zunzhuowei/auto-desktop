package com.hbs.auto.utils;

import groovy.lang.GroovyClassLoader;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用groovy将一段代码动态构造成一个类
 */
public class GroovyAutoBuildClassUtils {
    /**
     * 缓存Class类对象
     */
    private static Map<String, Class<?>> cache = new ConcurrentHashMap<>();
    /**
     * 缓存代码
     */
    private static Map<String, Object> codeCache = new ConcurrentHashMap<>();

    private static GroovyClassLoader classLoader = new GroovyClassLoader();

    //public GroovyAutoBuildClassUtils() {
        //初始化GroovyClassLoader类加载器
    //}

    /**
     * 使用groovy将一段代码动态构造成一个类，构造类时请指定一个classUuid，这个字段用来减少类的重复创建问题
     *
     * @param classUuid 接口的唯一标识
     * @param code      接口对应代码
     * @return
     */
    public static Class<?> buildClass(String classUuid, String code) {
        //如果代码有改变过或者没有加载过，都必须重新加载
        if (code == null || "".equals(code) || StringUtils.isEmpty(classUuid)) {
            return null;
        }
        Class<?> clazz = cache.get(classUuid);
        if (clazz == null || !code.equals(codeCache.get(classUuid))) {
            clazz = classLoader.parseClass(code);
            cache.put(classUuid, clazz);
            codeCache.put(classUuid, code);
        }
        return clazz;
    }

}
