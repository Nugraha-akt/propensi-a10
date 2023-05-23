package apap.propensi.mantra.contoller.restcontroller;

import apap.propensi.mantra.service.KomplainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/komplain")
public class KomplainRestController {
    final KomplainService komplainService;

    public KomplainRestController(KomplainService komplainService) {
        this.komplainService = komplainService;
    }

    @GetMapping("/resolved-count")
    public ResponseEntity<Map<String, Long>> resolvedGaugeChart() {
        try {
            Map<String, Long> resolvedCounts = new LinkedHashMap<>();
            resolvedCounts.put("All", komplainService.getAllCount());
            resolvedCounts.put("Resolved", komplainService.getSumClosed().longValue());
            return ResponseEntity.ok(resolvedCounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
    }
}
