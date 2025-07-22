package com.icodeap.facturacionprod.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "detalle_facturas")
@Getter
@Setter
public class DetalleFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idProducto;
    private Integer cantidad;
    private BigDecimal precio;
    private BigDecimal total;

    @CreationTimestamp
    private LocalDateTime fechaCreado;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_factura")
    private Factura factura;
}
