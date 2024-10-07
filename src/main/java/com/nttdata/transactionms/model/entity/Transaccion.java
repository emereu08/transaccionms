package com.nttdata.transactionms.model.entity;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "transacciones")
public class Transaccion {
    private String tipo;
    private double monto;
    private String fecha;
    private String cuentaOrigen;
    private String cuentaDestino;
}
