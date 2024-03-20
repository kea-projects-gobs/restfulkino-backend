package dk.kino.service.impl;

import dk.kino.entity.SeatPrice;
import dk.kino.exception.NotFoundException;
import dk.kino.repository.SeatPriceRepository;
import dk.kino.service.SeatPriceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatPriceServiceImpl implements SeatPriceService {
    private final SeatPriceRepository seatPriceRepository;

    public SeatPriceServiceImpl(SeatPriceRepository seatPriceRepository) {
        this.seatPriceRepository = seatPriceRepository;
    }

    @Override
    public Optional<SeatPrice> findByName(String name) {
        return seatPriceRepository.findById(name);
    }

    @Override
    public List<SeatPrice> findAll() {
        return seatPriceRepository.findAll();
    }

    @Override
    public SeatPrice createSeatPrice(SeatPrice seatPrice) {
        return seatPriceRepository.save(seatPrice);
    }

    @Override
    public SeatPrice updatePrices(String name, SeatPrice seatPrice) {
        SeatPrice existingSeatPrice = seatPriceRepository.findById(seatPrice.getName()).orElseThrow(() -> new NotFoundException("Could not find seat price name"));
        existingSeatPrice.setUnit(seatPrice.getUnit());
        existingSeatPrice.setAmount(seatPrice.getAmount());
        return seatPriceRepository.save(existingSeatPrice);
    }
}
