package com.icodeap.facturacionprod.dto;

import lombok.Data;

@Data
public class ResponseDetalleFacturaDTO {
    private Integer idProducto;
    private Integer cantidad;
    private java.math.BigDecimal precio;
    private java.math.BigDecimal total;

}
