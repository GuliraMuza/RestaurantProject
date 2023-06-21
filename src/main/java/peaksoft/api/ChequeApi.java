package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.simple.*;
import peaksoft.service.ChequeService;

@RestController
@RequestMapping("/api/cheque")
@RequiredArgsConstructor
@PermitAll
public class ChequeApi {

    private final ChequeService chequeService;

    @PostMapping("/save/cheque")
    public SimpleResponse saveCheque(@RequestBody ChequeRequest chequeRequest) {
        return chequeService.saveCheque(chequeRequest);
    }

    @GetMapping("/chequeRes")
    public ChequeOneDayRestaurantResponse getChequeRestaurant(@RequestBody ChequeOneDayRestaurantRequest chequeOneDayRestaurantRequest){
      return   chequeService.chequeOneDayRestaurant(chequeOneDayRestaurantRequest);
    }

    @GetMapping("/chequeWaiter")
    public ChequeOneDayWaiterResponse getChequeWaiter(@RequestBody ChequeOneDayWaiterRequest chequeOneDayWaiterRequest){
      return   chequeService.chequeOneDayWaiter(chequeOneDayWaiterRequest);
    }

}
