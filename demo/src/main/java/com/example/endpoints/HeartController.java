package com.example.endpoints;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.database.HeartEntity;
import com.example.enums.FeedTopic;
import com.example.model.heart.HeartRepository;
import com.example.notification.FeedNotificationEvent;
import com.example.service.CandidateNotificationService;

@Controller
public class HeartController {
    private static final String PAGE_VIEW = "hearts";
    private static final String PAGE_FRAGMENT = "hearts :: page-content";

    private final HeartRepository heartRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final CandidateNotificationService candidateNotificationService;

    public HeartController(HeartRepository heartRepository,
                           ApplicationEventPublisher eventPublisher,
                           CandidateNotificationService candidateNotificationService) {
        this.heartRepository = heartRepository;
        this.eventPublisher = eventPublisher;
        this.candidateNotificationService = candidateNotificationService;
    }

    @GetMapping("/hearts")
    public String list(Model model) {
        populateModel(model);
        return PAGE_VIEW;
    }

    @PostMapping("/hearts")
    public String create(@ModelAttribute HeartEntity heart,
                         @RequestHeader(value = "HX-Request", required = false) String hxRequest,
                         Model model) {
        HeartEntity persistedHeart = heartRepository.save(heart);
        eventPublisher.publishEvent(new FeedNotificationEvent(
            FeedTopic.NEW_HEART,
            String.format("Neues Herz registriert: %s (%s)",
                persistedHeart.getDonorName() == null ? "unbekannt" : persistedHeart.getDonorName(),
                persistedHeart.getBloodType() == null ? "?" : persistedHeart.getBloodType())
        ));
        candidateNotificationService.notifyForHeart(persistedHeart);

        if (isHtmxRequest(hxRequest)) {
            populateModel(model);
            return PAGE_FRAGMENT;
        }

        return "redirect:/hearts";
    }

    private void populateModel(Model model) {
        model.addAttribute("hearts", heartRepository.findAll());
        model.addAttribute("heart", new HeartEntity());
    }

    private boolean isHtmxRequest(String hxRequest) {
        return "true".equalsIgnoreCase(hxRequest);
    }
}
