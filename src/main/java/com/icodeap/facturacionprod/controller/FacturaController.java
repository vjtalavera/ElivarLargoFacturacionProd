package com.icodeap.facturacionprod.controller;

import com.icodeap.facturacionprod.dto.RequestFacturaDTO;
import com.icodeap.facturacionprod.dto.ResponseFacturaDTO;
import com.icodeap.facturacionprod.service.FacturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @PostMapping
    public ResponseFacturaDTO save(@RequestBody RequestFacturaDTO requestFacturaDTO){
        return facturaService.save(requestFacturaDTO);
    }

    @GetMapping
    public ResponseEntity<List<ResponseFacturaDTO>> findAll(){
        List<ResponseFacturaDTO> responseFacturaDTOS = facturaService.findAll();
        if (responseFacturaDTOS.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(responseFacturaDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseFacturaDTO> findById(@PathVariable Integer id){
        Optional<ResponseFacturaDTO> factura = facturaService.findById(id);
        return factura
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        facturaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
