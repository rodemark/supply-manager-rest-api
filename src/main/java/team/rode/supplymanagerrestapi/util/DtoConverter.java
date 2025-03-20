package team.rode.supplymanagerrestapi.util;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.rode.supplymanagerrestapi.DTO.response.DeliveryItemResponseDto;
import team.rode.supplymanagerrestapi.DTO.response.DeliveryResponseDto;
import team.rode.supplymanagerrestapi.models.Delivery;

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
    }

    // Delivery -> DeliveryResponseDto
    private void configureDeliveryMapping() {
        modelMapper.addMappings(new PropertyMap<Delivery, DeliveryResponseDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setDate(source.getDate());
                map().setSupplierId(source.getSupplier().getId());
                map().setSupplierName(source.getSupplier().getName());
                map().setDeliveryItemList(source.getDeliveryItemList().stream().map(item -> {
                    DeliveryItemResponseDto deliveryItemResponseDto = new DeliveryItemResponseDto();
                    deliveryItemResponseDto.setId(item.getId());
                    deliveryItemResponseDto.setPrice(item.getPrice());
                    deliveryItemResponseDto.setQuantity(item.getQuantity());
                    deliveryItemResponseDto.setProductId(item.getProduct().getId());

                    return deliveryItemResponseDto;
                }).toList());
                map().setTotalCost(source.getTotalCost());
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
