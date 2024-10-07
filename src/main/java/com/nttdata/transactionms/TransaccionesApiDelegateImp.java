package com.nttdata.transactionms;

import com.nttdata.transactionms.api.TransaccionesApiDelegate;
import com.nttdata.transactionms.business.TransaccionService;
import com.nttdata.transactionms.model.TransactionRequest;
import com.nttdata.transactionms.model.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionesApiDelegateImp implements TransaccionesApiDelegate {

    @Autowired
    private TransaccionService transaccionService;

    @Override
    public ResponseEntity<List<TransactionResponse>> consultarTransacciones() {
        return ResponseEntity.ok(transaccionService.consultarTransacciones());
    }

    @Override
    public  ResponseEntity<TransactionResponse> registrarDeposito(TransactionRequest transactionRequest) throws Exception {
        return ResponseEntity.ok(transaccionService.registrarDeposito(transactionRequest));
    }

    @Override
    public ResponseEntity<TransactionResponse> registrarRetiro(TransactionRequest transactionRequest) throws Exception {
        return ResponseEntity.ok(transaccionService.registrarRetiro(transactionRequest));
    }

    @Override
    public ResponseEntity<TransactionResponse> registrarTransferencia(TransactionRequest transactionRequest) throws Exception {
        return ResponseEntity.ok(transaccionService.registrarTransferencia(transactionRequest));
    }

}
