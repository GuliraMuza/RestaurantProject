package peaksoft.service;

import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.CheckResponse;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.simple.*;

public interface ChequeService {
    SimpleResponse saveCheque(ChequeRequest chequeRequest);


    CheckResponse getChequeById(Long chequeId);

    SimpleResponse deleteChequeById(Long chequeId);

    ChequeOneDayRestaurantResponse chequeOneDayRestaurant(ChequeOneDayRestaurantRequest chequeOneDayRestaurantRequest);

    /*  @Override
          public ChequeOneDayRestaurantResponse chequeOneDayRestaurant(ChequeOneDayRestaurantRequest chequeOneDayRestaurantRequest) {
              Restaurant restaurant = restaurantRepository.findById(chequeOneDayRestaurantRequest.getRestaurantId()).orElseThrow(() -> new NotFoundException("Not found"));
              int numberCheque=0;
              int totalSumma=0;
              int averageSumm=0;
              for (User user : restaurant.getUsers()) {
                  if(user.getRole().equals(Role.WAITER)){
                      for (Cheque cheque : user.getCheques()) {
                          if(cheque.getCreatedAt().isEqual(chequeOneDayRestaurantRequest.getData())){
                              int usluga=cheque.getPriceTotal()*restaurant.getService()/100;
                              totalSumma+=usluga+cheque.getPriceTotal();

                              numberCheque++;
                          }
                      }
                  }
              }

              return  ChequeOneDayRestaurantResponse.builder()
                      .priceAverage(totalSumma)
                      .numberCheque(numberCheque)
                      .date(chequeOneDayRestaurantRequest.getData())
                      .averageSumm(averageSumm)
                      .build();
          }*/
      //ChequeTotalWaiterResponse
      //ChequeWaiterRequest
    ChequeOneDayWaiterResponse chequeOneDayWaiter(ChequeOneDayWaiterRequest chequeWaiterRequest);


    //ChequeTotalWaiterResponse
    //ChequeWaiterRequest


    //ChequeTotalWaiterResponse
    //ChequeWaiterRequest
}
