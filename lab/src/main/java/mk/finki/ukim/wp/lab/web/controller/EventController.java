package mk.finki.ukim.wp.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.wp.lab.model.Comment;
import mk.finki.ukim.wp.lab.model.Event;
import mk.finki.ukim.wp.lab.model.Location;
import mk.finki.ukim.wp.lab.model.User;
import mk.finki.ukim.wp.lab.service.EventService;
import mk.finki.ukim.wp.lab.service.LocationService;
import mk.finki.ukim.wp.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final LocationService locationService;
    private final UserService userService;

    public EventController(EventService eventService, LocationService locationService, UserService userService) {
        this.eventService = eventService;
        this.locationService = locationService;
        this.userService = userService;
    }

    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String error, Model model, HttpServletRequest req) {
        List<Event> events = eventService.listAll();
        List<Location> locations=locationService.findAll();
        model.addAttribute("location", locations);
        String searchText = req.getParameter("searchText");
        String minRatingStr = req.getParameter("minRating");
        String locationID= req.getParameter("location");

        if (locationID != null && !locationID.trim().isEmpty()) {
            Long locationId=Long.parseLong(locationID);
            events = eventService.findByLocation(locationId);
        }

        if (searchText != null && !searchText.trim().isEmpty()) {
            events = eventService.searchEvents(searchText);
        }

        if (minRatingStr != null && !minRatingStr.trim().isEmpty()) {
            double minRating = Double.parseDouble(minRatingStr);
            events = events.stream().filter(event -> event.getPopularityScore() >= minRating).toList();
        }


        model.addAttribute("events", events);
        model.addAttribute("error", error);

        return "listEvents";

    }

    @GetMapping("/add-form")
    public String addEventPage(@RequestParam(required = false) String error, Model model) {
        List<Location> locations = this.locationService.findAll();


        model.addAttribute("location", locations);

        model.addAttribute("error", error);

        return "add-event";
    }


    @PostMapping("/add")
    public String saveEvent(@RequestParam String name,
                            @RequestParam String description,
                            @RequestParam double popularityScore,
                            @RequestParam Long locationID) {
        try {
            Event event = new Event(name, description, popularityScore, 1, locationService.findById(locationID).get());
            eventService.saveEvents(event);
            return "redirect:/events";
        }catch (RuntimeException e){
            return "redirect:/events/add-form?error="+e.getMessage();
        }
    }

    @GetMapping("/edit/{eventId}")
    public String getEditEventForm(@PathVariable Long eventId, Model model) {
        Optional<Event> optionalEvent = this.eventService.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            List<Location> locations = this.locationService.findAll();
            model.addAttribute("location", locations);
            model.addAttribute("event", event);
            return "add-event";
        }
        return "redirect:/events?error=EventNotFound";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        this.eventService.deleteById(id);
        return "redirect:/events";
    }

    @GetMapping("/details/{eventId}")
    public String details(@PathVariable Long eventId, Model model, @RequestParam(required = false) String error) {
       Event event = this.eventService.findById(eventId).get();
        List<Comment> comments = event.getComments();

        if (comments == null) {
            comments = new ArrayList<>();
        }

        if (comments.isEmpty()) {
            comments.add(new Comment("No comments yet", new User("Admin", "ad", "a", "d"),null));
        }

        model.addAttribute("comments", comments);
        model.addAttribute("event", event);
        model.addAttribute("error", error);
        return "details";
    }

    @PostMapping("/comments/{id}")
    public String saveComments(@PathVariable Long id,
                               @RequestParam String comment,
                               HttpServletRequest request) {
        Optional<Event> eventOptional = eventService.findById(id);
        if (eventOptional.isEmpty()) {
            return "redirect:/events?error=EventNotFound";
        }

        String username =  request.getRemoteUser();
        System.out.println(username);
        if (username.isEmpty()){
            return "redirect:/events/details/" + id + "?error=UserNotLoggedIn";
        }
        User user = userService.loadUserByUsername(username);


        Event event = eventOptional.get();
        Comment newComment = new Comment(comment, user,event);
        if (event.getComments() == null) {
            event.setComments(new ArrayList<>());
        }
        event.getComments().add(newComment);
        eventService.saveEventsCom(event);
        return "redirect:/events/details/" + id;
    }
}
