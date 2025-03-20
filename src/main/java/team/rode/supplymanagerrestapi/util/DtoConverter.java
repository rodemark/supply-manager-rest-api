package team.rode.supplymanagerrestapi.util;

import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.rode.supplymanagerrestapi.DTO.response.DeliveryItemResponseDto;
import team.rode.supplymanagerrestapi.DTO.response.DeliveryResponseDto;
import team.rode.supplymanagerrestapi.DTO.response.SupplierProductPriceResponseDto;
import team.rode.supplymanagerrestapi.models.Delivery;
import team.rode.supplymanagerrestapi.models.DeliveryItem;
import team.rode.supplymanagerrestapi.models.SupplierProductPrice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoConverter {
    private final ModelMapper modelMapper;

    @Autowired
    public DtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        configureDeliveryMapping();
        configureSupplierProductPrice();
    }

    // Delivery -> DeliveryResponseDto
    private void configureDeliveryMapping() {
        Converter<List<DeliveryItem>, List<DeliveryItemResponseDto>> deliveryItemConverter = ctx -> {
            List<DeliveryItem> items = ctx.getSource();
            if (items == null) {
                return new ArrayList<>();
            }
            return items.stream().map(item -> {
                DeliveryItemResponseDto dto = new DeliveryItemResponseDto();
                dto.setId(item.getId());
                dto.setPrice(item.getPrice());
                dto.setQuantity(item.getQuantity());
                dto.setProductId(item.getProduct().getId());
                dto.setProductName(item.getProduct().getName());
                return dto;
            }).collect(Collectors.toList());
        };

        modelMapper.addMappings(new PropertyMap<Delivery, DeliveryResponseDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setDate(source.getDate());
                map().setSupplierId(source.getSupplier().getId());
                map().setSupplierName(source.getSupplier().getName());
                using(deliveryItemConverter)
                        .map(source.getDeliveryItemList())
                        .setDeliveryItemList(null);
                map().setTotalCost(source.getTotalCost());
            }
        });
    }

    private void configureSupplierProductPrice() {
        modelMapper.addMappings(new PropertyMap<SupplierProductPrice, SupplierProductPriceResponseDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setPrice(source.getPrice());
                map().setProductId(source.getProduct().getId());
                map().setProductName(source.getProduct().getName());
                map().setSupplierId(source.getSupplier().getId());
                map().setSupplierName(source.getSupplier().getName());
                map().setStartDate(source.getStartDate());
                map().setEndDate(source.getEndDate());
            }
        });
    }


    public <D, T> D convertToDto(T entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public <D, T> T convertToEntity(D dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
}
