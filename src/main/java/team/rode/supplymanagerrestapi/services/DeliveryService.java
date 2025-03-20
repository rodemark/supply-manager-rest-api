package team.rode.supplymanagerrestapi.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.rode.supplymanagerrestapi.DTO.request.DeliveryItemRequestDto;
import team.rode.supplymanagerrestapi.DTO.request.DeliveryRequestDto;
import team.rode.supplymanagerrestapi.DTO.response.DeliveryResponseDto;
import team.rode.supplymanagerrestapi.models.Delivery;
import team.rode.supplymanagerrestapi.models.DeliveryItem;
import team.rode.supplymanagerrestapi.models.SupplierProductPrice;
import team.rode.supplymanagerrestapi.repositories.DeliveryItemRepository;
import team.rode.supplymanagerrestapi.repositories.DeliveryRepository;
import team.rode.supplymanagerrestapi.util.DtoConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DtoConverter dtoConverter;
    private final EntityRetrievalService entityRetrievalService;
    private final DeliveryItemRepository deliveryItemRepository;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, DtoConverter dtoConverter,
                           EntityRetrievalService entityRetrievalService, DeliveryItemRepository deliveryItemRepository) {
        this.deliveryRepository = deliveryRepository;
        this.dtoConverter = dtoConverter;
        this.entityRetrievalService = entityRetrievalService;
        this.deliveryItemRepository = deliveryItemRepository;
    }

    public List<DeliveryResponseDto> getDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        return deliveries.stream()
                .map(delivery -> dtoConverter.convertToDto(delivery, DeliveryResponseDto.class))
                .toList();
    }

    @Transactional
    public DeliveryResponseDto addDelivery(DeliveryRequestDto deliveryRequestDto) {
        Delivery delivery = new Delivery();

        setDeliveryRequestDtoParamToDelivery(delivery, deliveryRequestDto);

        delivery = deliveryRepository.save(delivery);
        log.info("Delivery with id {} added ", delivery.getId());

        return dtoConverter.convertToDto(delivery, DeliveryResponseDto.class);
    }

    @Transactional
    public void deleteDelivery(Long deliveryId) {
        Delivery delivery = entityRetrievalService.getDeliveryById(deliveryId);
        deliveryRepository.delete(delivery);
        log.info("Delivery with id {} deleted ", deliveryId);
    }

    @Transactional
    public DeliveryResponseDto editDelivery(DeliveryRequestDto deliveryRequestDto, Long deliveryId) {
        Delivery delivery = entityRetrievalService.getDeliveryById(deliveryId);

        setDeliveryRequestDtoParamToDelivery(delivery, deliveryRequestDto);

        delivery = deliveryRepository.save(delivery);
        return dtoConverter.convertToDto(delivery, DeliveryResponseDto.class);
    }

    private void setDeliveryRequestDtoParamToDelivery(Delivery delivery, DeliveryRequestDto deliveryRequestDto) {
        Long supplierId = deliveryRequestDto.getSupplierId();
        LocalDate date = deliveryRequestDto.getDate();
        delivery.setSupplier(entityRetrievalService.getSupplierById(supplierId));
        delivery.setDate(date);

        List<DeliveryItem> newItems = new ArrayList<>();
        for (DeliveryItemRequestDto deliveryItemRequestDto : deliveryRequestDto.getDeliveryItemList()) {
            Long productId = deliveryItemRequestDto.getProductId();
            SupplierProductPrice supplierProductPrice = entityRetrievalService.getSupplierProductPrice(supplierId, productId, date);

            DeliveryItem deliveryItem = new DeliveryItem();
            deliveryItem.setDelivery(delivery);
            deliveryItem.setPrice(supplierProductPrice.getPrice());
            deliveryItem.setProduct(entityRetrievalService.getProductById(productId));
            deliveryItem.setQuantity(deliveryItemRequestDto.getQuantity());
            newItems.add(deliveryItem);
        }

        // Вместо того чтобы присваивать новый список,
        // очищаем существующую коллекцию и добавляем в неё новые элементы
        if (delivery.getDeliveryItemList() == null) {
            delivery.setDeliveryItemList(new ArrayList<>());
        } else {
            delivery.getDeliveryItemList().clear();
        }
        delivery.getDeliveryItemList().addAll(newItems);
    }

}