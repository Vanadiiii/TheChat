package me.imatveev.thechat.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("permitAll()")
public class FormUserController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
