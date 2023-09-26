package com.uwu.study.dp.behavioral.iterator.aggregate;



import com.uwu.study.dp.behavioral.iterator.Book;
import com.uwu.study.dp.behavioral.iterator.iterator.BookIterator;
import com.uwu.study.dp.behavioral.iterator.iterator.Iterator;

import java.util.ArrayList;
import java.util.List;

public class BookAggregate implements Aggregate{
    private List<Book> list = new ArrayList<Book>();

    public void Add(Book book) {
        list.add(book);
    }

    public Book get(int index) {
        return list.get(index);
    }

    public int getSize() {
        return list.size();
    }

    @Override
    public Iterator createIterator() {
        return new BookIterator(this);
    }
}
