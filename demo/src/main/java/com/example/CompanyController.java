package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CompanyController {
    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping("/companies")
    public String list(Model model) {
        model.addAttribute("companies", companyRepository.findAll());
        model.addAttribute("company", new CompanyEntity());
        model.addAttribute("topics", FeedTopic.values());
        return "companies";
    }

    @PostMapping("/companies")
    public String create(@ModelAttribute CompanyEntity company) {
        companyRepository.save(company);
        return "redirect:/companies";
    }

    @PostMapping("/companies/{companyId}/subscriptions/subscribe")
    public String subscribe(@PathVariable Long companyId, @RequestParam FeedTopic topic) {
        CompanyEntity company = companyRepository.findById(companyId).orElseThrow();
        company.subscribe(topic);
        companyRepository.save(company);
        return "redirect:/companies";
    }

    @PostMapping("/companies/{companyId}/subscriptions/unsubscribe")
    public String unsubscribe(@PathVariable Long companyId, @RequestParam FeedTopic topic) {
        CompanyEntity company = companyRepository.findById(companyId).orElseThrow();
        company.unsubscribe(topic);
        companyRepository.save(company);
        return "redirect:/companies";
    }
}
