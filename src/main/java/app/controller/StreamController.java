package app.controller;

import app.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class StreamController {

    @Autowired
    private StreamService streamService;

    @PostMapping("broadcast")
    public void createBroadcast() {
        streamService.createBroadcast(null);
    }

    @PutMapping("stream")
    public void startStream() {
        streamService.startStream(null);
    }

    @DeleteMapping("stream")
    public void finishStream() {
        streamService.finishStream();
    }
}
