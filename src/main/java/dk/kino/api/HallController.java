package dk.kino.api;

import dk.kino.dto.HallDTO;
import dk.kino.service.HallService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping
    public ResponseEntity<List<HallDTO>> getHalls() {
        List<HallDTO> halls = hallService.findAll();
        return ResponseEntity.ok(halls);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HallDTO> getHallById(@PathVariable int id) {
        HallDTO hall = hallService.findById(id);
        return ResponseEntity.ok(hall);
    }

    @GetMapping("/{hallName}/cinemas/{cinemaName}")
    public ResponseEntity<HallDTO> findByNameAndCinemaName(@PathVariable String hallName, @PathVariable String cinemaName) {
        return ResponseEntity.ok(hallService.findByNameAndCinemaName(hallName,cinemaName));
    }

    @GetMapping("/cinema/{cinemaId}")
    public ResponseEntity<List<HallDTO>> findHallsByCinemaId(@PathVariable int cinemaId) {
        List<HallDTO> halls = hallService.findHallsByCinemaId(cinemaId);
        return ResponseEntity.ok(halls);
    }

    @PostMapping
    public ResponseEntity<HallDTO> addHall(@RequestBody HallDTO hallDTO) {
        HallDTO createHall = hallService.createHall(hallDTO);
        return ResponseEntity.ok(createHall);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HallDTO> updateHall(@PathVariable int id, @RequestBody HallDTO hallDTO) {
        HallDTO updatedHall = hallService.updateHall(id, hallDTO);
        if (updatedHall != null) {
            return ResponseEntity.ok(updatedHall);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HallDTO> deleteHall(@PathVariable int id) {
        hallService.deleteHall(id);
        return ResponseEntity.ok().build();
    }
}
