package com.handwritten.spring.core;

/**
 * Bean定义，描述Bean的元数据信息
 */
public class BeanDefinition {
    private String beanName;
    private Class<?> beanClass;
    private String scope = "singleton"; // 默认单例
    private boolean isLazy = false; // 是否懒加载
    
    public BeanDefinition() {}
    
    public BeanDefinition(String beanName, Class<?> beanClass) {
        this.beanName = beanName;
        this.beanClass = beanClass;
    }
    
    // Getters and Setters
    public String getBeanName() {
        return beanName;
    }
    
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
    
    public Class<?> getBeanClass() {
        return beanClass;
    }
    
    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }
    
    public String getScope() {
        return scope;
    }
    
    public void setScope(String scope) {
        this.scope = scope;
    }
    
    public boolean isLazy() {
        return isLazy;
    }
    
    public void setLazy(boolean lazy) {
        isLazy = lazy;
    }
    
    public boolean isSingleton() {
        return "singleton".equals(scope);
    }
    
    @Override
    public String toString() {
        return "BeanDefinition{" +
                "beanName='" + beanName + '\'' +
                ", beanClass=" + beanClass +
                ", scope='" + scope + '\'' +
                ", isLazy=" + isLazy +
                '}';
    }
}