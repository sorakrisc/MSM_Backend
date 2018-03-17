package msm_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("test")
public class TestController {


    @GetMapping("/whoami")
    public ResponseEntity whoami(Authentication auth){
        Map<String, String> ret = new HashMap<String, String>(){{
            put("user", (String) auth.getPrincipal());
        }};
        return ResponseEntity.ok(ret);
    }
}
