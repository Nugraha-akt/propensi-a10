package apap.propensi.mantra.contoller.restcontroller;

import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.service.RequestService;
import apap.propensi.mantra.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/request")
public class RequestRestController {
    private final RequestService requestService;
    private final UserService userService;

    public RequestRestController(RequestService requestService, UserService userService) {
        this.requestService = requestService;
        this.userService = userService;
    }

    @GetMapping("/overview")
    public ResponseEntity<Map<String, Long>> getOverviewRequest(Principal principal) {
        try {
            UserModel user = userService.getUserByUsername(principal.getName());
            Map<String, Long> statusCount = requestService.getCountOfRequestsByStatus(user);
            return ResponseEntity.ok(statusCount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/created-history")
    public ResponseEntity<Map<String, Long>> getRequestCounts() {
        try {
            Map<String, Long> requestCounts = requestService.getRequestCounts();
            return ResponseEntity.ok(requestCounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
    }
}