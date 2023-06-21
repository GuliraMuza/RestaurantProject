package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtService;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.CheckResponse;
import peaksoft.dto.simple.*;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final JwtService jwtService;

    @Override
    public SimpleResponse saveCheque(ChequeRequest chequeRequest) {
        User user = userRepository.findById(chequeRequest.waiterId()).orElseThrow();
        Cheque cheque = new Cheque();
        List<MenuItem> menuItemList = new ArrayList<>();
        int sum = 0;
        for (Long manuItem : chequeRequest.manuItems()) {
            MenuItem menuItem = menuItemRepository.findById(manuItem).orElseThrow(() -> new NotFoundException("This manu item is not found"));
            menuItem.addCheque(cheque);
            menuItemList.add(menuItem);
            sum += menuItem.getPrice();
        }
        cheque.setUser(user);
        cheque.setCreatedAt(LocalDate.now());
        cheque.setPriceTotal(sum);
        cheque.setMenuItems(menuItemList);
         chequeRepository.save(cheque);
         return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully saved!!").build();
    }

    @Override
    public CheckResponse getChequeById(Long chequeId) {
        CheckResponse checkResponse = chequeRepository.getChequeId(chequeId).orElseThrow(
                () -> new NotFoundException("Cheque with id: %s not found".formatted(chequeId)));
        checkResponse.setMenuItems(chequeRepository.getAllMenuItemsByChequeId(chequeId));
        return checkResponse;
    }

    @Override
    public SimpleResponse deleteChequeById(Long chequeId) {
        return null;
    }


    @Override
    public ChequeOneDayRestaurantResponse chequeOneDayRestaurant(ChequeOneDayRestaurantRequest chequeOneDayRestaurantRequest) {
        int sumCheck=0;
        int waiterCheck=0;
        int averageTotal=0;
        Restaurant restaurant = restaurantRepository.findById(chequeOneDayRestaurantRequest.getRestaurantId()).orElseThrow();
        for (User user : restaurant.getUsers()) {
            if(user.getRole().equals(Role.WAITER)){
                for (Cheque cheque : user.getCheques()) {
                    if(cheque.getCreatedAt().isEqual(chequeOneDayRestaurantRequest.getData())){
                        int service = cheque.getPriceTotal()*restaurant.getService()/100;
                        averageTotal += service+cheque.getPriceTotal();
                        sumCheck = averageTotal/service;
                        waiterCheck++;
                    }
                }
            }

        }

        return ChequeOneDayRestaurantResponse.builder()
                .restaurantName(restaurant.getName())
                .averageTotal(averageTotal)
                .waiterCheck(waiterCheck)
                .sumCheck(sumCheck)
                .date(chequeOneDayRestaurantRequest.getData())
                .build();
    }




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
    @Override
    public  ChequeOneDayWaiterResponse chequeOneDayWaiter(ChequeOneDayWaiterRequest chequeWaiterRequest) {
        int counterCheck = 0;
        int totalPrice = 0;
        User user = userRepository.findById(chequeWaiterRequest.getWaiterId()).orElseThrow();
        if(user.getRole().equals(Role.WAITER)){
            for (Cheque cheque : user.getCheques()) {
                if( cheque.getCreatedAt().isEqual(chequeWaiterRequest.getDate())){
                    int service = cheque.getPriceTotal()*user.getRestaurant().getService()/100;
                    totalPrice += service + cheque.getPriceTotal();
                    counterCheck++;
                }
            }
        }
        return  ChequeOneDayWaiterResponse .builder()
                .waiterName(user.getFirstName()+" "+user.getLastName())
                .counterCheck(counterCheck)
                .date(chequeWaiterRequest.getDate())
                .totalPrice(totalPrice)
                .build();
    }}

/*
    @Override
    public ChequeOneDayWaiterResponse chequeOneDayWaiter(ChequeOneDayWaiterRequest chequeOneDayWaiterRequest) {
        User user = userRepository.findById(chequeOneDayWaiterRequest.getWaiterId()).orElseThrow(() -> new NotFoundException("not fond"));
        int chequeCounter=0;
        int totalSumma=0;
        if(user.getRole().equals(Role.WAITER)){
            for (Cheque cheque : user.getCheques()) {
                if(cheque.getCreatedAt().isEqual(chequeOneDayWaiterRequest.getDate())){
                    int usluga=cheque.getPriceAverage()*user.getRestaurant().getService()/100;
                    totalSumma+=usluga+cheque.getPriceAverage();
                    chequeCounter++;
                }
            }
        }
        return ChequeOneDayWaiterResponse.builder()
                .totalSumma(totalSumma)
                .numberOfCheques(chequeCounter)
                .waiterFullName(user.getFirstName()+" "+user.getLastName())
                .date(chequeOneDayWaiterRequest.getDate())
                .build();}}
*/
