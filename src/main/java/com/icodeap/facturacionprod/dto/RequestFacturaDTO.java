package com.icodeap.facturacionprod.dto;

import lombok.Data;

@Data
public class RequestFacturaDTO {
    private Integer id;
    private String numeroFactura;
    private java.util.Set<RequestDetalleFacturaDTO> detalleFacturas;
}
