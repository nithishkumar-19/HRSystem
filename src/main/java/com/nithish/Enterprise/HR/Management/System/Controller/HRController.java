package com.nithish.Enterprise.HR.Management.System.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HRController {

    @GetMapping("/hr/test")
    public String test() {
        return "Welcome HR";
    }
}
