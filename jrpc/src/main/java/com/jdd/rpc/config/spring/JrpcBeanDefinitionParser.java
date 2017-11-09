package com.jdd.rpc.config.spring;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * jrpc相关bean定义转化
 * <p>
 *
 */
public class JrpcBeanDefinitionParser implements BeanDefinitionParser {
    /** 使用的javabean */
    private final Class<?> beanClass;

    /**
     * @param beanClass
     */
    public JrpcBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return parse(element, parserContext, beanClass);
    }

    /**
     * 实现{@link#parse}
     * <p>
     * 
     * @param element
     * @param parserContext
     * @param clazz
     * @return {@link BeanDefinition}
     */
    private BeanDefinition parse(Element element, ParserContext parserContext, Class<?> clazz) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(clazz);

        Method[] methods = clazz.getMethods();
        String id = StringUtils.EMPTY;
        for (Method method : methods) {
            if (method.getName().length() > 3 && method.getName().startsWith("set") && method.getParameterTypes().length == 1) {
                String attribute = method.getName().substring(3);
                char ch = attribute.charAt(0);
                attribute = Character.toLowerCase(ch) + attribute.substring(1);

                String value = element.getAttribute(attribute);

                if (StringUtils.isNotEmpty(value)) {
                    Type type = method.getParameterTypes()[0];
                    if (type == boolean.class) {
                        beanDefinition.getPropertyValues().addPropertyValue(attribute, Boolean.valueOf(value));
                    } else {
                        if ("ref".equals(attribute) && parserContext.getRegistry().containsBeanDefinition(value)) {
                            beanDefinition.getPropertyValues().addPropertyValue(attribute, new RuntimeBeanReference(value));
                        } else {
                            beanDefinition.getPropertyValues().addPropertyValue(attribute, value);
                            if ("id".equals(attribute)) {
                                id = value;
                            }
                        }
                    }
                }
            }
        }
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

        return beanDefinition;
    }

}
