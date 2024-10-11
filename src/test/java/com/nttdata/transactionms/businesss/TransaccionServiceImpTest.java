package com.nttdata.transactionms.businesss;

import com.nttdata.transactionms.business.TransaccionMapper;
import com.nttdata.transactionms.business.TransaccionServiceImp;
import com.nttdata.transactionms.model.TransactionResponse;
import com.nttdata.transactionms.model.entity.Transaccion;
import com.nttdata.transactionms.repository.TransaccionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class TransaccionServiceImpTest {

    @Mock
    TransaccionRepository transaccionRepository;

    @Mock
    TransaccionMapper transaccionMapper;

    @InjectMocks
    TransaccionServiceImp transaccionServiceImp;

    @Test
    @DisplayName("when get list transacciones ok")
    void whenGetListTransaccionesOk(){
        Transaccion transaccion = new Transaccion();
        transaccion.setTipo("Deposito");
        transaccion.setMonto(555.99);
        transaccion.setFecha("10/10/2024");
        transaccion.setCuentaDestino("12345");
        TransactionResponse response1 = transaccionMapper.getTransaccionResponse(transaccion);

        Transaccion transaccion2 = new Transaccion();
        transaccion.setTipo("Retiro");
        transaccion.setMonto(555.99);
        transaccion.setFecha("10/10/2024");
        transaccion.setCuentaDestino("12345");
        TransactionResponse response2 = transaccionMapper.getTransaccionResponse(transaccion2);

        Transaccion transaccion3 = new Transaccion();
        transaccion.setTipo("Transferencia");
        transaccion.setMonto(555.99);
        transaccion.setFecha("10/10/2024");
        transaccion.setCuentaDestino("12345");
        transaccion.setCuentaDestino("54321");
        TransactionResponse response3 = transaccionMapper.getTransaccionResponse(transaccion3);

        List<Transaccion> lista = new ArrayList<>();
        lista.add(transaccion);
        lista.add(transaccion2);
        lista.add(transaccion3);

        Mockito.when(transaccionRepository.findAll()).thenReturn(lista);

        assert.

    }

}
