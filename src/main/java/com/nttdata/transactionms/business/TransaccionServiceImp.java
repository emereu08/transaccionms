package com.nttdata.transactionms.business;

import com.nttdata.transactionms.model.TransactionRequest;
import com.nttdata.transactionms.model.TransactionResponse;
import com.nttdata.transactionms.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransaccionServiceImp implements TransaccionService{

    @Autowired
    TransaccionRepository transaccionRepository;

    @Autowired
    TransaccionMapper transaccionMapper;

    @Autowired
    HttpConection con;

    @Override
    public List<TransactionResponse> consultarTransacciones() {
        System.out.println(transaccionRepository.findAll());
        return transaccionRepository.findAll().stream()
                .map(m -> transaccionMapper.getTransaccionResponse(m))
                .collect(Collectors.toList());
    }

    @Override
    public TransactionResponse registrarDeposito(TransactionRequest request) throws Exception {
        String tipo = "Deposito";
        Map<String, Object> datosCuenta = con.getCuentaByNumeroCuenta();
        if(!datosCuenta.get("numeroCuenta").toString().equals(request.getCuentaOrigen()))
            throw new Exception("El numero de cuenta no existe");
        return transaccionMapper.getTransaccionResponse(transaccionRepository.save(transaccionMapper.getTransaccion(request, tipo)));
    }

    @Override
    public TransactionResponse registrarRetiro(TransactionRequest request) throws Exception {
        String tipo = "Retiro";
        Map<String, Object> datosCuenta = con.getCuentaByNumeroCuenta();
        if(!datosCuenta.get("numeroCuenta").toString().equals(request.getCuentaOrigen()))
            throw new Exception("El numero de cuenta no existe");
        return transaccionMapper.getTransaccionResponse(transaccionRepository.save(transaccionMapper.getTransaccion(request, tipo)));
    }

    @Override
    public TransactionResponse registrarTransferencia(TransactionRequest request) throws Exception{
        String tipo = "Transferencia";
        Map<String, Object> datosCuenta = con.getCuentaByNumeroCuenta();
        if(!datosCuenta.get("numeroCuenta").toString().equals(request.getCuentaOrigen()))
            throw new Exception("El numero de cuenta no existe");
        return transaccionMapper.getTransaccionResponse(transaccionRepository.save(transaccionMapper.getTransaccion(request, tipo)));
    }
}
