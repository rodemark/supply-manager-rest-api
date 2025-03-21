package team.rode.supplymanagerrestapi.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.rode.supplymanagerrestapi.DTO.request.SupplierRequestDto;
import team.rode.supplymanagerrestapi.DTO.response.SupplierResponseDto;
import team.rode.supplymanagerrestapi.exceptionHandling.exceptions.DuplicateResourceException;
import team.rode.supplymanagerrestapi.models.Supplier;
import team.rode.supplymanagerrestapi.repositories.SupplierRepository;
import team.rode.supplymanagerrestapi.util.DtoConverter;

import java.util.List;

@Service
@Slf4j
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final DtoConverter dtoConverter;
    private final EntityRetrievalService entityRetrievalService;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository, DtoConverter dtoConverter, EntityRetrievalService entityRetrievalService) {
        this.supplierRepository = supplierRepository;
        this.dtoConverter = dtoConverter;
        this.entityRetrievalService = entityRetrievalService;
    }

    public List<SupplierResponseDto> getSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();

        return suppliers.stream().map(supplier -> dtoConverter.convertToDto(supplier, SupplierResponseDto.class)).toList();
    }

    @Transactional
    public SupplierResponseDto addSupplier(SupplierRequestDto supplierRequestDto) {
        if (supplierRepository.existsSimilarSupplier(supplierRequestDto.getName(), supplierRequestDto.getContact())) {
            log.error("A Supplier with such data already exists");
            throw new DuplicateResourceException("A Supplier with such data already exists");
        }
        Supplier supplier = supplierRepository.save(dtoConverter.convertToEntity(supplierRequestDto, Supplier.class));

        log.info("Supplier with id {} added ", supplier.getId());

        return dtoConverter.convertToDto(supplier, SupplierResponseDto.class);
    }

    @Transactional
    public void deleteSupplier(Long supplierId) {
        Supplier supplier = entityRetrievalService.getSupplierById(supplierId);
        supplierRepository.delete(supplier);
        log.info("Supplier with id {} deleted ", supplierId);
    }

    @Transactional
    public SupplierResponseDto editSupplier(SupplierRequestDto supplierRequestDto, Long supplierId) {
        Supplier supplier = entityRetrievalService.getSupplierById(supplierId);

        supplier.setName(supplierRequestDto.getName());
        supplier.setContact(supplierRequestDto.getContact());
        supplier = supplierRepository.save(supplier);

        return dtoConverter.convertToDto(supplier, SupplierResponseDto.class);
    }
}