package com.icodeap.facturacionprod.repository;

import com.icodeap.facturacionprod.model.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Integer> {
}
