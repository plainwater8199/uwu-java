package com.handwritten.spring.context;

import com.handwritten.spring.annotation.Autowired;
import com.handwritten.spring.annotation.Component;
import com.handwritten.spring.annotation.ComponentScan;
import com.handwritten.spring.core.BeanDefinition;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于注解的应用上下文实现
 * Spring容器的核心实现类
 */
public class AnnotationConfigApplicationContext implements ApplicationContext {
    
    // 存储BeanDefinition的Map，key为beanName，value为BeanDefinition
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    
    // 单例Bean的缓存，key为beanName，value为bean实例
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    
    // 正在创建的Bean集合，用于解决循环依赖
    private Set<String> singletonsCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>());
    
    // 配置类
    private Class<?> configClass;
    
    /**
     * 构造函数，传入配置类
     */
    public AnnotationConfigApplicationContext(Class<?> configClass) {
        this.configClass = configClass;
        
        // 1. 扫描组件，生成BeanDefinition
        scan();
        
        // 2. 实例化所有非懒加载的单例Bean
        preInstantiateSingletons();
    }
    
    /**
     * 扫描组件，生成BeanDefinition
     */
    private void scan() {
        // 获取ComponentScan注解
        ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
        if (componentScan == null) {
            throw new RuntimeException("配置类必须标注@ComponentScan注解");
        }
        
        // 获取扫描路径
        String[] scanPaths = componentScan.value();
        if (scanPaths.length == 0) {
            scanPaths = componentScan.basePackages();
        }
        if (scanPaths.length == 0) {
            // 默认扫描配置类所在包
            scanPaths = new String[]{configClass.getPackage().getName()};
        }
        
        // 扫描每个包路径
        for (String scanPath : scanPaths) {
            scanPackage(scanPath);
        }
        
        System.out.println("扫描完成，共找到 " + beanDefinitionMap.size() + " 个Bean定义");
    }
    
    /**
     * 扫描指定包路径
     */
    private void scanPackage(String packageName) {
        // 将包名转换为文件路径
        String packagePath = packageName.replace(".", "/");
        
        // 获取类加载器
        ClassLoader classLoader = AnnotationConfigApplicationContext.class.getClassLoader();
        URL resource = classLoader.getResource(packagePath);
        
        if (resource == null) {
            return;
        }
        
        File directory = new File(resource.getFile());
        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }
        
        // 递归扫描目录
        scanDirectory(directory, packageName, classLoader);
    }
    
    /**
     * 递归扫描目录
     */
    private void scanDirectory(File directory, String packageName, ClassLoader classLoader) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        
        for (File file : files) {
            if (file.isDirectory()) {
                // 递归扫描子目录
                scanDirectory(file, packageName + "." + file.getName(), classLoader);
            } else if (file.getName().endsWith(".class")) {
                // 处理class文件
                String className = packageName + "." + file.getName().replace(".class", "");
                try {
                    Class<?> clazz = classLoader.loadClass(className);
                    
                    // 检查是否标注了@Component注解
                    if (clazz.isAnnotationPresent(Component.class)) {
                        Component component = clazz.getAnnotation(Component.class);
                        
                        // 确定bean名称
                        String beanName = component.value();
                        if (beanName.isEmpty()) {
                            // 默认为类名首字母小写
                            beanName = clazz.getSimpleName();
                            beanName = Character.toLowerCase(beanName.charAt(0)) + beanName.substring(1);
                        }
                        
                        // 创建BeanDefinition
                        BeanDefinition beanDefinition = new BeanDefinition(beanName, clazz);
                        beanDefinitionMap.put(beanName, beanDefinition);
                        
                        System.out.println("发现组件: " + beanName + " -> " + clazz.getName());
                    }
                } catch (ClassNotFoundException e) {
                    System.err.println("无法加载类: " + className);
                }
            }
        }
    }
    
    /**
     * 预实例化所有非懒加载的单例Bean
     */
    private void preInstantiateSingletons() {
        System.out.println("开始实例化单例Bean...");
        
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            
            // 只实例化单例且非懒加载的Bean
            if (beanDefinition.isSingleton() && !beanDefinition.isLazy()) {
                getBean(beanName);
            }
        }
        
        System.out.println("单例Bean实例化完成，共实例化 " + singletonObjects.size() + " 个Bean");
    }
    
    @Override
    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new RuntimeException("没有找到名为 '" + beanName + "' 的Bean定义");
        }
        
        // 如果是单例Bean，先从缓存中获取
        if (beanDefinition.isSingleton()) {
            Object singletonBean = singletonObjects.get(beanName);
            if (singletonBean != null) {
                return singletonBean;
            }
            
            // 检查循环依赖
            if (singletonsCurrentlyInCreation.contains(beanName)) {
                throw new RuntimeException("检测到循环依赖: " + beanName);
            }
            
            // 标记正在创建
            singletonsCurrentlyInCreation.add(beanName);
            
            try {
                // 创建Bean实例
                singletonBean = createBean(beanDefinition);
                
                // 缓存单例Bean
                singletonObjects.put(beanName, singletonBean);
                
                return singletonBean;
            } finally {
                // 移除创建标记
                singletonsCurrentlyInCreation.remove(beanName);
            }
        } else {
            // 原型Bean，每次都创建新实例
            return createBean(beanDefinition);
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        // 根据类型查找Bean
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            BeanDefinition beanDefinition = entry.getValue();
            if (requiredType.isAssignableFrom(beanDefinition.getBeanClass())) {
                return (T) getBean(entry.getKey());
            }
        }
        
        throw new RuntimeException("没有找到类型为 '" + requiredType.getName() + "' 的Bean");
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName, Class<T> requiredType) {
        Object bean = getBean(beanName);
        if (!requiredType.isInstance(bean)) {
            throw new RuntimeException("Bean '" + beanName + "' 不是所需类型 '" + requiredType.getName() + "'");
        }
        return (T) bean;
    }
    
    @Override
    public boolean containsBean(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }
    
    @Override
    public Class<?> getType(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        return beanDefinition != null ? beanDefinition.getBeanClass() : null;
    }
    
    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }
    
    /**
     * 创建Bean实例
     */
    private Object createBean(BeanDefinition beanDefinition) {
        try {
            Class<?> beanClass = beanDefinition.getBeanClass();
            
            // 1. 实例化Bean（调用无参构造函数）
            Object beanInstance = beanClass.getDeclaredConstructor().newInstance();
            
            // 2. 依赖注入（处理@Autowired注解的字段）
            populateBean(beanInstance);
            
            System.out.println("创建Bean: " + beanDefinition.getBeanName() + " -> " + beanInstance.getClass().getName());
            
            return beanInstance;
        } catch (InstantiationException | IllegalAccessException | 
                 InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("创建Bean失败: " + beanDefinition.getBeanName(), e);
        }
    }
    
    /**
     * 依赖注入，处理@Autowired注解的字段
     */
    private void populateBean(Object beanInstance) {
        Class<?> beanClass = beanInstance.getClass();
        
        // 获取所有字段
        Field[] fields = beanClass.getDeclaredFields();
        
        for (Field field : fields) {
            // 检查是否标注了@Autowired注解
            if (field.isAnnotationPresent(Autowired.class)) {
                Autowired autowired = field.getAnnotation(Autowired.class);
                
                try {
                    // 根据字段类型获取Bean
                    Object dependencyBean = getBean(field.getType());
                    
                    // 设置字段可访问
                    field.setAccessible(true);
                    
                    // 注入依赖
                    field.set(beanInstance, dependencyBean);
                    
                    System.out.println("依赖注入: " + beanClass.getSimpleName() + "." + field.getName() + 
                                     " <- " + dependencyBean.getClass().getSimpleName());
                    
                } catch (Exception e) {
                    if (autowired.required()) {
                        throw new RuntimeException("依赖注入失败: " + beanClass.getName() + "." + field.getName(), e);
                    }
                }
            }
        }
    }
}