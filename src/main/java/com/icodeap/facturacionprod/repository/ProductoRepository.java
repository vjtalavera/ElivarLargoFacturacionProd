package com.icodeap.facturacionprod.repository;

import com.icodeap.facturacionprod.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
