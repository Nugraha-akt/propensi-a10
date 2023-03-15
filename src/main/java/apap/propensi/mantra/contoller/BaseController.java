package apap.propensi.mantra.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
    @GetMapping("/")
    private String Home() {
        return "index";
    }

    @GetMapping("/dashboard")
    private String Dashboard() {
        return "dashboard";
    }

}
