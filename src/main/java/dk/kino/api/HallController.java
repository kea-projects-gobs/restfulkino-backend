package dk.kino.api;

import dk.kino.dto.HallDTO;
import dk.kino.service.HallService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get all halls", description = "Returns a list of all halls.")
    @GetMapping
    public ResponseEntity<List<HallDTO>> getHalls() {
        List<HallDTO> halls = hallService.findAll();
        return ResponseEntity.ok(halls);
    }

    @Operation(summary = "Get hall by id", description = "Returns a single hall, if one with the given id exists.")
    @GetMapping("/{id}")
    public ResponseEntity<HallDTO> getHallById(@PathVariable int id) {
        HallDTO hall = hallService.findById(id);
        return ResponseEntity.ok(hall);
    }

    @Operation(summary = "Get hall by name and cinema name", description = "Returns a single hall, if one with the given name and cinema name exists.")
    @GetMapping("/{hallName}/cinemas/{cinemaName}")
    public ResponseEntity<HallDTO> findByNameAndCinemaName(@PathVariable String hallName, @PathVariable String cinemaName) {
        return ResponseEntity.ok(hallService.findByNameAndCinemaName(hallName,cinemaName));
    }

    @Operation(summary = "Get halls by cinema id", description = "Returns a list of halls in a cinema with the given id.")
    @GetMapping("/cinema/{cinemaId}")
    public ResponseEntity<List<HallDTO>> findHallsByCinemaId(@PathVariable int cinemaId) {
        List<HallDTO> halls = hallService.findHallsByCinemaId(cinemaId);
        return ResponseEntity.ok(halls);
    }

    @Operation(summary = "Create a hall", description = "Creates a new hall.")
    @PostMapping
    public ResponseEntity<HallDTO> addHall(@RequestBody HallDTO hallDTO) {
        HallDTO createHall = hallService.createHall(hallDTO);
        return ResponseEntity.ok(createHall);
    }

    @Operation(summary = "Update a hall", description = "Updates a hall.")
    @PutMapping("/{id}")
    public ResponseEntity<HallDTO> updateHall(@PathVariable int id, @RequestBody HallDTO hallDTO) {
        HallDTO updatedHall = hallService.updateHall(id, hallDTO);
        if (updatedHall != null) {
            return ResponseEntity.ok(updatedHall);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a hall", description = "Soft deletes a hall, by flagging it as inactive.")
    @DeleteMapping("/{id}")
    public ResponseEntity<HallDTO> deleteHall(@PathVariable int id) {
        hallService.deleteHall(id);
        return ResponseEntity.ok().build();
    }
}
