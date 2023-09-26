package com.uwu.study.dp.behavioral.iterator.iterator;


import com.uwu.study.dp.behavioral.iterator.aggregate.BookAggregate;

public class BookIterator implements Iterator{

        private int index;
        private BookAggregate bookAggregate;

        public BookIterator(BookAggregate bookAggregate) {
                this.index = 0;
                this.bookAggregate = bookAggregate;
        }


        @Override
        public boolean hasNext() {
               return index < bookAggregate.getSize();
        }

        @Override
        public Object next() {
                Object obj = bookAggregate.get(index);
                index++;

                return obj;
        }
}
