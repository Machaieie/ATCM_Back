package com.bussinessmanagement.managementSystem.Exceptions;

public class InsufficientStockException extends BusinessException {
    public InsufficientStockException(String productName) {
        super("Estoque insuficiente para o produto: " + productName);
    }
}

