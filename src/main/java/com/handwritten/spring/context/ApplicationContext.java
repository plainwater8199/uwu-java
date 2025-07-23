package com.handwritten.spring.context;

/**
 * 应用上下文接口，定义Spring容器的核心功能
 */
public interface ApplicationContext {
    
    /**
     * 根据bean名称获取bean实例
     */
    Object getBean(String beanName);
    
    /**
     * 根据类型获取bean实例
     */
    <T> T getBean(Class<T> requiredType);
    
    /**
     * 根据名称和类型获取bean实例
     */
    <T> T getBean(String beanName, Class<T> requiredType);
    
    /**
     * 判断是否包含指定名称的bean
     */
    boolean containsBean(String beanName);
    
    /**
     * 获取指定bean的类型
     */
    Class<?> getType(String beanName);
    
    /**
     * 获取所有bean的名称
     */
    String[] getBeanDefinitionNames();
}