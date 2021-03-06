package com.pheiot.bamboo.common.utils.reflect;

import com.pheiot.bamboo.common.utils.base.ExceptionUtil.UncheckedException;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

public class ReflectionUtilTest {

    @Test
    public void getAndSetFieldValue() {
        TestBean bean = new TestBean();
        // 无需getter函数, 直接读取privateField
        assertThat((int) ReflectionUtil.getFieldValue(bean, "privateField")).isEqualTo(1);

        // 先尝试getter函数, 然后直接读取privateField
        assertThat((int) ReflectionUtil.getProperty(bean, "privateField")).isEqualTo(1);

        // 绕过将publicField+1的getter函数,直接读取publicField的原始值
        assertThat((int) ReflectionUtil.getFieldValue(bean, "publicField")).isEqualTo(1);
        // 先尝试getter函数, 成功则补不直接读取publicField
        assertThat((int) ReflectionUtil.getProperty(bean, "publicField")).isEqualTo(2);

        bean = new TestBean();
        // 无需setter函数, 直接设置privateField
        ReflectionUtil.setFieldValue(bean, "privateField", 2);
        assertThat(bean.inspectPrivateField()).isEqualTo(2);
        ReflectionUtil.setProperty(bean, "privateField", 3);
        assertThat(bean.inspectPrivateField()).isEqualTo(3);

        // 绕过将publicField+1的setter函数,直接设置publicField的原始值
        ReflectionUtil.setFieldValue(bean, "publicField", 2);
        assertThat(bean.inspectPublicField()).isEqualTo(2);

        // 没有绕过将publicField+1的setter函数
        ReflectionUtil.setProperty(bean, "publicField", 3);
        assertThat(bean.inspectPublicField()).isEqualTo(4);

        try {
            ReflectionUtil.getFieldValue(bean, "notExist");
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {

        }

        try {
            ReflectionUtil.setFieldValue(bean, "notExist", 2);
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void invokeGetterAndSetter() {
        TestBean bean = new TestBean();
        assertThat((int) ReflectionUtil.invokeGetter(bean, "publicField")).isEqualTo(bean.inspectPublicField() + 1);

        MethodInvoker invoker = MethodInvoker.createGetter(TestBean.class, "publicField");
        assertThat((int) invoker.invoke(bean)).isEqualTo(bean.inspectPublicField() + 1);

        bean = new TestBean();
        // 通过setter的函数将+1
        ReflectionUtil.invokeSetter(bean, "publicField", 10);
        assertThat(bean.inspectPublicField()).isEqualTo(10 + 1);

        MethodInvoker invoker2 = MethodInvoker.createSetter(TestBean.class, "publicField", Integer.class);
        invoker2.invoke(bean, 12);
        assertThat(bean.inspectPublicField()).isEqualTo(12 + 1);
    }

    @Test
    public void invokeMethod() {
        TestBean bean = new TestBean();
        // 使用函数名+参数类型的匹配, 支持传参数
        assertThat(ReflectionUtil.invokeMethod(bean, "privateMethod", new Object[]{"calvin"}).toString())
                .isEqualTo("hello calvin");

        // 使用函数名+参数类型的匹配
        assertThat(ReflectionUtil.invokeMethod(bean, "privateMethod", new Object[]{"calvin"},
                new Class[]{String.class}).toString()).isEqualTo("hello calvin");

        // MethodInvoker
        MethodInvoker invoker = MethodInvoker.createMethod(bean.getClass(), "privateMethod", String.class);
        assertThat(invoker.invoke(bean, new Object[]{"calvin"}).toString()).isEqualTo("hello calvin");

        // 仅匹配函数名
        assertThat(ReflectionUtil.invokeMethodByName(bean, "privateMethod", new Object[]{"calvin"}).toString())
                .isEqualTo("hello calvin");

        // 函数名错
        try {
            ReflectionUtil.invokeMethod(bean, "notExistMethod", new Object[]{"calvin"},
                    new Class[]{String.class});
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {

        }

        // 函数名错
        try {
            MethodInvoker.createMethod(bean.getClass(), "notExistMethod", String.class);
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {

        }

        // 参数类型错
        try {
            ReflectionUtil.invokeMethod(bean, "privateMethod", new Object[]{"calvin"},
                    new Class[]{Integer.class});
            failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (RuntimeException e) {

        }

        // 参数类型错
        try {
            MethodInvoker.createMethod(bean.getClass(), "privateMethod", Integer.class);
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {

        }

        // 函数名错
        try {
            ReflectionUtil.invokeMethodByName(bean, "notExistMethod", new Object[]{"calvin"});
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void invokeConstructor() {
        TestBean bean = ReflectionUtil.invokeConstructor(TestBean.class);
        assertThat(bean.getPublicField()).isEqualTo(2);

        TestBean3 bean3 = ReflectionUtil.invokeConstructor(TestBean3.class, 4);
        assertThat(bean3.getId()).isEqualTo(4);
    }

    @Test
    public void convertReflectionExceptionToUnchecked() {
        IllegalArgumentException iae = new IllegalArgumentException();
        // ReflectionException,normal
        RuntimeException e = ReflectionUtil.convertReflectionExceptionToUnchecked(iae);
        assertThat(e).isEqualTo(iae);

        // InvocationTargetException,extract it's target exception.
        Exception ex = new Exception();
        e = ReflectionUtil.convertReflectionExceptionToUnchecked(new InvocationTargetException(ex));
        assertThat(e.getCause()).isEqualTo(ex);

        // UncheckedException, ignore it.
        RuntimeException re = new RuntimeException("abc");
        e = ReflectionUtil.convertReflectionExceptionToUnchecked(re);
        assertThat(e).hasMessage("abc");

        // Unexcepted Checked exception.
        e = ReflectionUtil.convertReflectionExceptionToUnchecked(ex);
        assertThat(e).isInstanceOf(UncheckedException.class);
    }

    public static class ParentBean<T, ID> {
    }

    public static class TestBean extends ParentBean<String, Long> {
        /**
         * 没有getter/setter的field
         */
        private int privateField = 1;
        /**
         * 有getter/setter的field
         */
        private int publicField = 1;

        // 通過getter函數會比屬性值+1
        public int getPublicField() {
            return publicField + 1;
        }

        // 通過setter函數會被比輸入值加1
        public void setPublicField(int publicField) {
            this.publicField = publicField + 1;
        }

        public int inspectPrivateField() {
            return privateField;
        }

        public int inspectPublicField() {
            return publicField;
        }

        private String privateMethod(String text) {
            return "hello " + text;
        }
    }

    public static class TestBean2 extends ParentBean {
    }

    public static class TestBean3 {

        public TestBean3() {

        }

        public TestBean3(int id) {
            super();
            this.id = id;
        }

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
