package com.uwu.study.dp.create.prototype;


/**
 * 原型模式：克隆
 *  复制一个已经存在的对象
 */
public class PrototypeTest {
    public static void main(String[] args) {

        Product product1 = new Product(2022,5.28);
        System.out.println(product1.getId()+"--"+ product1.getPrice());

        Product product2 = (Product) product1.Clone();
        System.out.println(product2.getId()+"--"+ product2.getPrice());


        Product product3 = (Product) product2.Clone();
        System.out.println(product3.getId()+"--"+ product3.getPrice());

    }
}
