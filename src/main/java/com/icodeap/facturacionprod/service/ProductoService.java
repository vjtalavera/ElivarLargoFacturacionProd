package com.icodeap.facturacionprod.service;

import com.icodeap.facturacionprod.dto.ProductoDTO;
import com.icodeap.facturacionprod.model.Producto;
import com.icodeap.facturacionprod.repository.ProductoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ModelMapper modelMapper;

    public ProductoService(ProductoRepository productoRepository, ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
    }

    public ProductoDTO save(ProductoDTO productoDTO){
        Producto producto = modelMapper.map(productoDTO, Producto.class);
        return modelMapper.map(productoRepository.save(producto), ProductoDTO.class);
    }

    public List<ProductoDTO> findAll(){
        return productoRepository.findAll().stream()
                .map(producto -> modelMapper.map(producto, ProductoDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<ProductoDTO> findById(Integer id){
        return productoRepository
                        .findById(id)
                        .map(producto -> modelMapper.map(producto, ProductoDTO.class));
    }

    public boolean deleteById(Integer id){
        return productoRepository
                .findById(id)
                        .map(producto -> {
                            productoRepository.deleteById(id);
                            return true;
                        })
                                .orElse(false);
    }

    public Optional<ProductoDTO> update(ProductoDTO productoDTO){
        Producto producto = modelMapper.map(productoDTO, Producto.class);

        return productoRepository
                .findById(producto.getId())
                .map(productoDB -> modelMapper.map(productoRepository.save(producto), ProductoDTO.class));
    }
}
