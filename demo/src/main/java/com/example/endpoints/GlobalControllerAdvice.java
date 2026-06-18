package com.example.endpoints;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute
    public void addGlobalAttributes(HttpServletRequest request, Model model) {
        if (request != null) {
            model.addAttribute("currentPath", request.getRequestURI());
        } else {
            model.addAttribute("currentPath", "");
        }
    }
}
