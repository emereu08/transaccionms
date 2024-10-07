package com.nttdata.transactionms.business;

import com.nttdata.transactionms.model.TransactionRequest;
import com.nttdata.transactionms.model.TransactionResponse;

import java.util.List;

public interface TransaccionService {

    List<TransactionResponse> consultarTransacciones();

    TransactionResponse registrarDeposito(TransactionRequest request) throws Exception;

    TransactionResponse registrarRetiro(TransactionRequest request) throws Exception;

    TransactionResponse registrarTransferencia(TransactionRequest request) throws Exception;
}
