package com.uwu.study.dp.behavioral.iterator;


import com.uwu.study.dp.behavioral.iterator.aggregate.BookAggregate;
import com.uwu.study.dp.behavioral.iterator.iterator.Iterator;

/**
 * 提供一种方法来访问聚合对象，而不用暴露这个对象的内部表示，其别名为游标(Cursor)。迭代器模式是一种对象行为型模式。
 *
 * 特点：
 * 1、它支持以不同的方式遍历一个聚合对象。
 * 2、迭代器简化了聚合类。
 * 3、在同一个聚合上可以有多个遍历。
 * 4、在迭代器模式中，增加新的聚合类和迭代器类都很方便，无须修改原有代码，满足“开闭原则”的要求。
 */
public class IteratorPatternTest {
    public static void main(String[] args) {
        BookAggregate bookAggregate = new BookAggregate();

        String[] books = {"数据结构", "操作系统", "计算机网络", "计算机组成原理"};
        double[] prices = {10.24, 20.48, 40.96, 81.92};

        for (int i = 0; i < 4; i ++ ) {
            bookAggregate.Add(new Book(books[i], prices[i]));
        }

        Iterator bookIterator = bookAggregate.createIterator();
        while (bookIterator.hasNext()) {
            Book book = (Book) bookIterator.next();
            System.out.println(book.getName() + " " + book.getPrice());
        }

    }
}
