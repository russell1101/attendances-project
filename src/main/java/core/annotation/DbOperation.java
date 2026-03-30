package core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


// 標記此方法涉及資料庫異動 (insert, update, delete) 寫入log
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbOperation {
	// 傳入操作說明
	String action() default "未知操作";
}