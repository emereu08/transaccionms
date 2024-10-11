package com.nttdata.transactionms.business;

import com.nttdata.transactionms.model.TransactionRequest;
import com.nttdata.transactionms.model.TransactionResponse;
import com.nttdata.transactionms.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    HttpConection httpConection;

    @Override
    public List<TransactionResponse> consultarTransacciones() {
        System.out.println(transaccionRepository.findAll());
        return transaccionRepository.findAll().stream()
                        .map(m -> transaccionMapper.getTransaccionResponse(m))
                        .collect(Collectors.toList());
    }

    @Override
    public TransactionResponse registrarDeposito(TransactionRequest request) {
        String tipo = "Deposito";
        try{
            Map<String, Object> datosCuenta = getCuentaByNumeroCuenta(request.getCuentaOrigen());
            if(datosCuenta.isEmpty())
                throw new Exception("El numero de cuenta no existe");
            Double id = (Double) datosCuenta.get("id");
            Map<String, Object> deposito = depositar(id.intValue(), request.getMonto());
            if(deposito.isEmpty())
                throw new Exception("Error al depositar");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return transaccionMapper.getTransaccionResponse(transaccionRepository.save(transaccionMapper.getTransaccion(request, tipo)));
    }

    @Override
    public TransactionResponse registrarRetiro(TransactionRequest request) {
        String tipo = "Retiro";
        try{
            Map<String, Object> datosCuenta = getCuentaByNumeroCuenta(request.getCuentaOrigen());
            if(datosCuenta.isEmpty())
                throw new Exception("El numero de cuenta no existe");
            Double saldo = (double) datosCuenta.get("saldo");
            if(request.getMonto() > saldo)
                throw new Exception("Saldo insuficiente");
            Double id = (Double) datosCuenta.get("id");
            Map<String, Object> retirar = retirar(id.intValue(), request.getMonto());
            if(retirar.isEmpty())
                throw new Exception("Error al retirar");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return transaccionMapper.getTransaccionResponse(transaccionRepository.save(transaccionMapper.getTransaccion(request, tipo)));
    }

    @Override
    public TransactionResponse registrarTransferencia(TransactionRequest request) throws Exception{
        String tipo = "Transferencia";
        try{
            Map<String, Object> datosCuentaOrigen = getCuentaByNumeroCuenta(request.getCuentaOrigen());
            if(datosCuentaOrigen.isEmpty())
                throw new Exception("El numero de cuenta no existe");
            Map<String, Object> datosCuentaDestino = getCuentaByNumeroCuenta(request.getCuentaDestino());
            if(datosCuentaDestino.isEmpty())
                throw new Exception("El numero de cuenta no existe");
            Double saldo = (double) datosCuentaOrigen.get("saldo");
            if(request.getMonto() > saldo)
                throw new Exception("Saldo insuficiente");
            Double idRetiro = (Double) datosCuentaOrigen.get("id");
            Map<String, Object> retirar = retirar(idRetiro.intValue(), request.getMonto());
            if(retirar.isEmpty())
                throw new Exception("Error al retirar");
            Double idDeposito = (Double) datosCuentaDestino.get("id");
            Map<String, Object> deposito = depositar(idDeposito.intValue(), request.getMonto());
            if(deposito.isEmpty())
                throw new Exception("Error al depositar");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return transaccionMapper.getTransaccionResponse(transaccionRepository.save(transaccionMapper.getTransaccion(request, tipo)));
    }

    private Map<String, Object> getCuentaByNumeroCuenta(String numeroCuenta)  {
        Map<String, Object> cuenta = new HashMap<>();
        List<Map<String, Object>> listaCuentas = new ArrayList<>();
        String uri = Constantes.URL_CUENTAMS;
        try{
            listaCuentas = httpConection.stringToArray(httpConection.ejecutarSolicitudHttp(uri, "GET").toString());
            cuenta = listaCuentas.stream().filter(m -> m.get("numeroCuenta").equals(numeroCuenta)).findFirst().orElse(null);
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }

        return cuenta;
    }

    private Map<String, Object> depositar(Integer id, Double monto)  {
        Map<String, Object> deposito = new HashMap<>();
        String uri = Constantes.URL_CUENTAMS+"/"+id+"/depositar";
        String body = "{\"monto\":\""+monto+"\"}";
        try{
            deposito = httpConection.stringToMap(httpConection.ejecutarSolicitudHttp(uri, "PUT", body).toString());
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }

        return deposito;
    }

    private Map<String, Object> retirar(Integer id, Double monto)  {
        Map<String, Object> retiro = new HashMap<>();
        String uri = Constantes.URL_CUENTAMS+"/"+id+"/retirar";
        String body = "{\"monto\":\""+monto+"\"}";
        try{
            retiro = httpConection.stringToMap(httpConection.ejecutarSolicitudHttp(uri, "PUT", body).toString());
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }

        return retiro;
    }
}
