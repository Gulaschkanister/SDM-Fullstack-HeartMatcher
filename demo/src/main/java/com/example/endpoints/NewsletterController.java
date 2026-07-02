package com.example.endpoints;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.database.CompanyEntity;
import com.example.enums.FeedTopic;
import com.example.model.company.CompanyRepository;
import com.example.notification.NewsletterEntry;
import com.example.notification.NewsletterRepository;

@Controller
public class NewsletterController {
    private static final String PAGE_VIEW = "newsletter";
    private static final String PAGE_FRAGMENT = "newsletter :: page-content";

    private final NewsletterRepository newsletterRepository;
    private final CompanyRepository companyRepository;

    public NewsletterController(NewsletterRepository newsletterRepository, CompanyRepository companyRepository) {
        this.newsletterRepository = newsletterRepository;
        this.companyRepository = companyRepository;
    }

    @GetMapping("/newsletter")
    public String feed(@RequestParam(required = false) FeedTopic topic,
                       @RequestParam(required = false) Long companyId,
                       @RequestHeader(value = "HX-Request", required = false) String hxRequest,
                       Model model) {
        populateModel(model, topic, companyId);

        if (isHtmxRequest(hxRequest)) {
            return PAGE_FRAGMENT;
        }

        return PAGE_VIEW;
    }

    private void populateModel(Model model, FeedTopic topic, Long companyId) {
        List<CompanyEntity> companies = companyRepository.findAll();
        Optional<CompanyEntity> selectedCompany = companyId == null
                ? Optional.empty()
                : companyRepository.findById(companyId);

        List<NewsletterEntry> entries = newsletterRepository.findAll();
        if (topic != null) {
            entries = entries.stream()
                    .filter(entry -> entry.getTopic() == topic)
                    .toList();
        }

        if (selectedCompany.isPresent()) {
            CompanyEntity company = selectedCompany.get();
            entries = entries.stream()
                    .filter(entry -> company.getSubscriptions().contains(entry.getTopic()))
                    .toList();
        }

        model.addAttribute("selectedTopic", topic);
        model.addAttribute("selectedCompanyId", companyId);
        model.addAttribute("selectedCompany", selectedCompany.orElse(null));
        model.addAttribute("companies", companies);
        model.addAttribute("topics", FeedTopic.values());
        model.addAttribute("entries", entries);
    }

    private boolean isHtmxRequest(String hxRequest) {
        return "true".equalsIgnoreCase(hxRequest);
    }
}
