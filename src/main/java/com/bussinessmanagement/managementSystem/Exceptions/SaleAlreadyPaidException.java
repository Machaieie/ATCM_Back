package com.bussinessmanagement.managementSystem.Exceptions;

public class SaleAlreadyPaidException extends BusinessException {
    public SaleAlreadyPaidException() {
        super("Venda já está totalmente paga");
    }
}

