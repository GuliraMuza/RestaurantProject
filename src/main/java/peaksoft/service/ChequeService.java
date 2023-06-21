package peaksoft.service;

import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.simple.*;

public interface ChequeService {
    SimpleResponse saveCheque(ChequeRequest chequeRequest);
    ChequeResponse getChequeById(Long chequeId);
    SimpleResponse deleteChequeById(Long chequeId);



    ChequeOneDayRestaurantResponse chequeOneDayRestaurant(ChequeOneDayRestaurantRequest chequeOneDayRestaurantRequest);
    ChequeOneDayWaiterResponse chequeOneDayWaiter(ChequeOneDayWaiterRequest chequeOneDayWaiterRequest);


}
