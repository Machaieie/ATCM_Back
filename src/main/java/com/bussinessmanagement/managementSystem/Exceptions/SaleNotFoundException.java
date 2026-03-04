package com.bussinessmanagement.managementSystem.Exceptions;

public class SaleNotFoundException extends BusinessException {
    public SaleNotFoundException() {
        super("Venda não encontrada");
    }
}

