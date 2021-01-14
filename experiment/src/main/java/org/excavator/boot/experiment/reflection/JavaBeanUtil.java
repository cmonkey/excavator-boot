package org.excavator.boot.experiment.reflection;

import scala.Tuple2;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Function;

public class JavaBeanUtil {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    public static Tuple2<? extends Class, Function> createTupleWithReturnTypeAndGetter(Method getterMethod){
        try {
            return Tuple2.apply(getterMethod.getReturnType(),
                    (Function) createCallSite(LOOKUP.unreflect(getterMethod)).getTarget().invokeExact());
        }catch(Throwable e){
            throw new IllegalArgumentException("lambda creation failed for getterMethod ( " + getterMethod.getName() + ").", e);
        }
    }
    private static CallSite createCallSite(MethodHandle getterMethodHandle) throws LambdaConversionException {
        return LambdaMetafactory.metafactory(LOOKUP, "apply",
                MethodType.methodType(Function.class),
                MethodType.methodType(Object.class, Object.class),
                getterMethodHandle, getterMethodHandle.type());
    }

    public static boolean isGetterMethod(Method method) {
        return method.getParameterCount() == 0 &&
                !Modifier.isStatic(method.getModifiers()) &&
                method.getName().startsWith("get") &&
                !method.getName().endsWith("Class");
    }
}
