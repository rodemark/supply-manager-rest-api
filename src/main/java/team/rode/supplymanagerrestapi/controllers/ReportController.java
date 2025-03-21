package team.rode.supplymanagerrestapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.rode.supplymanagerrestapi.DTO.response.ReportResponseByDeliveryItemDto;
import team.rode.supplymanagerrestapi.services.ReportService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<ReportResponseByDeliveryItemDto> getReport(@RequestParam Long supplierId, @RequestParam String startDate,
                                                           @RequestParam String endDate, @RequestParam(required = false) Long productId) {

        return reportService.getReport(supplierId, startDate, endDate, productId);
    }
}
