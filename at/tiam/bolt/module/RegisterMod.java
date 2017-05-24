package at.tiam.bolt.module;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by quicktime on 5/24/17.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterMod {
    String name();
    String desc();
    int defaultKey() default -1;
    Module.Category category() default Module.Category.PLUGIN;
}
