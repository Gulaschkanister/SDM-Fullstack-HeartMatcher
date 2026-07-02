package com.example.endpoints;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.database.CompanyEntity;
import com.example.enums.FeedTopic;
import com.example.model.company.CompanyRepository;

@Controller
public class CompanyController {
    private static final String PAGE_VIEW = "companies";
    private static final String PAGE_FRAGMENT = "companies :: page-content";

    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping("/companies")
    public String list(Model model) {
        populateModel(model);
        return PAGE_VIEW;
    }

    @PostMapping("/companies")
    public String create(@ModelAttribute CompanyEntity company,
                         @RequestHeader(value = "HX-Request", required = false) String hxRequest,
                         Model model) {
        companyRepository.save(company);
        if (isHtmxRequest(hxRequest)) {
            populateModel(model);
            return PAGE_FRAGMENT;
        }
        return "redirect:/companies";
    }

    @PostMapping("/companies/{companyId}/subscriptions/subscribe")
    public String subscribe(@PathVariable Long companyId,
                            @RequestParam FeedTopic topic,
                            @RequestHeader(value = "HX-Request", required = false) String hxRequest,
                            Model model) {
        CompanyEntity company = companyRepository.findById(companyId).orElseThrow();
        company.subscribe(topic);
        companyRepository.save(company);
        if (isHtmxRequest(hxRequest)) {
            populateModel(model);
            return PAGE_FRAGMENT;
        }
        return "redirect:/companies";
    }

    @PostMapping("/companies/{companyId}/subscriptions/unsubscribe")
    public String unsubscribe(@PathVariable Long companyId,
                              @RequestParam FeedTopic topic,
                              @RequestHeader(value = "HX-Request", required = false) String hxRequest,
                              Model model) {
        CompanyEntity company = companyRepository.findById(companyId).orElseThrow();
        company.unsubscribe(topic);
        companyRepository.save(company);
        if (isHtmxRequest(hxRequest)) {
            populateModel(model);
            return PAGE_FRAGMENT;
        }
        return "redirect:/companies";
    }

    private void populateModel(Model model) {
        model.addAttribute("companies", companyRepository.findAll());
        model.addAttribute("company", new CompanyEntity());
        model.addAttribute("topics", FeedTopic.values());
    }

    private boolean isHtmxRequest(String hxRequest) {
        return "true".equalsIgnoreCase(hxRequest);
    }
}
