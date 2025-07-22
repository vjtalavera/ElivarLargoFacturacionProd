package com.icodeap.facturacionprod.service;

import com.icodeap.facturacionprod.dto.RequestDetalleFacturaDTO;
import com.icodeap.facturacionprod.dto.RequestFacturaDTO;
import com.icodeap.facturacionprod.dto.ResponseFacturaDTO;
import com.icodeap.facturacionprod.model.DetalleFactura;
import com.icodeap.facturacionprod.model.Factura;
import com.icodeap.facturacionprod.model.Producto;
import com.icodeap.facturacionprod.repository.FacturaRepository;
import com.icodeap.facturacionprod.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;
    private final ProductoRepository productoRepository;
    private final ModelMapper modelMapper;

    public FacturaService(FacturaRepository facturaRepository, ProductoRepository productoRepository, ModelMapper modelMapper) {
        this.facturaRepository = facturaRepository;
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
    }

    public List<ResponseFacturaDTO> findAll(){
        return facturaRepository
                .findAll()
                .stream()
                .map(factura -> modelMapper.map(factura, ResponseFacturaDTO.class))
                .toList();
    }

    public Optional<ResponseFacturaDTO> findById(Integer id){
        return facturaRepository
                .findById(id)
                .map(factura -> modelMapper.map(factura, ResponseFacturaDTO.class));
    }

    public void deleteById(Integer id) {
        facturaRepository.deleteById(id);
    }

    @Transactional
    public ResponseFacturaDTO save(RequestFacturaDTO requestFacturaDTO) {

        Factura factura = new Factura();
        BigDecimal subTotalFactura = BigDecimal.ZERO;
        Set<DetalleFactura> detalles = new HashSet<>();

        factura.setNumeroFactura(requestFacturaDTO.getNumeroFactura());

        for (RequestDetalleFacturaDTO detalleFacturaDTO : requestFacturaDTO.getDetalleFacturas()) {
            Producto producto = productoRepository
                    .findById(detalleFacturaDTO.getIdProducto())
                    .orElseThrow(()->new RuntimeException("Producto no encontrado"));

            BigDecimal totalProducto = producto.getPrecio()
                    .multiply(BigDecimal.valueOf(detalleFacturaDTO.getCantidad()));

            subTotalFactura = subTotalFactura.add(totalProducto);

            DetalleFactura detalleFactura = new DetalleFactura();
            detalleFactura.setFactura(factura);
            detalleFactura.setIdProducto(detalleFacturaDTO.getIdProducto());
            detalleFactura.setCantidad(detalleFacturaDTO.getCantidad());
            detalleFactura.setPrecio(producto.getPrecio());
            detalleFactura.setTotal(totalProducto);

            detalles.add(detalleFactura);
        }
        factura.setDetalleFacturas(detalles);
        factura.setSubTotal(subTotalFactura);
        factura.setTotal(subTotalFactura
                .add(subTotalFactura.multiply(BigDecimal.valueOf(factura.getIVA()))));

        Factura savedFactura = facturaRepository.save(factura);
        return modelMapper.map(savedFactura, ResponseFacturaDTO.class);

    }
}
