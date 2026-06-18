package com.example;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HeartController {
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
        model.addAttribute("hearts", heartRepository.findAll());
        model.addAttribute("heart", new HeartEntity());
        return "hearts";
    }

    @PostMapping("/hearts")
    public String create(@ModelAttribute HeartEntity heart) {
        HeartEntity savedHeart = heartRepository.save(heart);
        eventPublisher.publishEvent(new FeedNotificationEvent(
            FeedTopic.NEW_HEART,
            String.format("Neues Herz registriert: %s (%s)",
                savedHeart.getDonorName() == null ? "unbekannt" : savedHeart.getDonorName(),
                savedHeart.getBloodType() == null ? "?" : savedHeart.getBloodType())
        ));
        candidateNotificationService.notifyForHeart(savedHeart);
        return "redirect:/hearts";
    }
}
