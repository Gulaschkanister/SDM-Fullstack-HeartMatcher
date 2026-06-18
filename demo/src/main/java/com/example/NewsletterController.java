package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class NewsletterController {
    private final NewsletterRepository newsletterRepository;
    private final CompanyRepository companyRepository;

    public NewsletterController(NewsletterRepository newsletterRepository, CompanyRepository companyRepository) {
        this.newsletterRepository = newsletterRepository;
        this.companyRepository = companyRepository;
    }

    @GetMapping("/newsletter")
    public String feed(@RequestParam(required = false) FeedTopic topic,
                       @RequestParam(required = false) Long companyId,
                       Model model) {
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
        return "newsletter";
    }
}
