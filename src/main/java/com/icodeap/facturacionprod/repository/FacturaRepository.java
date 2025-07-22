package com.icodeap.facturacionprod.repository;

import com.icodeap.facturacionprod.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {
}
