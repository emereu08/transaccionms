package com.nttdata.transactionms.business;

import com.nttdata.transactionms.model.TransactionRequest;
import com.nttdata.transactionms.model.TransactionResponse;
import com.nttdata.transactionms.model.entity.Transaccion;
import org.springframework.stereotype.Component;

@Component
public class TransaccionMapper {

    public Transaccion getTransaccion(TransactionRequest request, String tipo){
        Transaccion entity = new Transaccion();
        entity.setMonto(request.getMonto());
        entity.setFecha(request.getFecha());
        entity.setCuentaOrigen(request.getCuentaOrigen());
        entity.setCuentaDestino(request.getCuentaDestino());
        entity.setTipo(tipo);
        return entity;
    }

    public TransactionResponse getTransaccionResponse(Transaccion transaccion){
        TransactionResponse response = new TransactionResponse();
        //response.setId(transaccion.getId());
        response.setTipo(transaccion.getTipo());
        response.setMonto(transaccion.getMonto());
        response.setFecha(transaccion.getFecha());
        response.setCuentaOrigen(transaccion.getCuentaOrigen());
        response.setCuentaDestino(transaccion.getCuentaDestino());
        return response;
    }

}
