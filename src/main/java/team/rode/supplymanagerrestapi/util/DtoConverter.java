package team.rode.supplymanagerrestapi.util;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.rode.supplymanagerrestapi.DTO.response.SupplierResponseDto;
import team.rode.supplymanagerrestapi.models.Supplier;

@Component
public class DtoConverter {
    private final ModelMapper modelMapper;

    @Autowired
    public DtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        configureSupplierRequestDtoMapping();
    }

    // Supplier -> SupplierResponseDto
    private void configureSupplierRequestDtoMapping() {
        modelMapper.addMappings(new PropertyMap<Supplier, SupplierResponseDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
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
