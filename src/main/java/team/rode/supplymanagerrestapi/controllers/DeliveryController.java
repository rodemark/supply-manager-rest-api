package team.rode.supplymanagerrestapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.rode.supplymanagerrestapi.DTO.request.DeliveryRequestDto;
import team.rode.supplymanagerrestapi.DTO.response.DeliveryResponseDto;
import team.rode.supplymanagerrestapi.services.DeliveryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public List<DeliveryResponseDto> getDeliveries() {
        return deliveryService.getDeliveries();
    }

    @PostMapping
    public DeliveryResponseDto addDelivery(@RequestBody DeliveryRequestDto deliveryRequestDto) {
        return deliveryService.addDelivery(deliveryRequestDto);
    }

    @PutMapping("/{id}")
    public DeliveryResponseDto editDelivery(@RequestBody DeliveryRequestDto deliveryRequestDto,
                                            @PathVariable Long id) {
        return deliveryService.editDelivery(deliveryRequestDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
    }
}
