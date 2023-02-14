package com.example.demo1.bean.other;

public interface OrderBookItem<T> {
    String getPrice();

    T getSize();
}
