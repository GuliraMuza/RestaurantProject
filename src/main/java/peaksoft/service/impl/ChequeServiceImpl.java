package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.ChequeResponse;
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
        cheque.setPriceAverage(sum);
        cheque.setMenuItems(menuItemList);
         chequeRepository.save(cheque);
         return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully saved!!").build();
    }

    @Override
    public ChequeResponse getChequeById(Long chequeId) {
        return null;
    }

    @Override
    public SimpleResponse deleteChequeById(Long chequeId) {
        return null;
    }



    @Override
    public ChequeOneDayRestaurantResponse chequeOneDayRestaurant(ChequeOneDayRestaurantRequest chequeOneDayRestaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(chequeOneDayRestaurantRequest.getRestaurantId()).orElseThrow(() -> new NotFoundException("Not found"));
        int numberCheque=0;
        int totalSumma=0;
        int averageSumm=0;
        for (User user : restaurant.getUsers()) {
            if(user.getRole().equals(Role.WAITER)){
                for (Cheque cheque : user.getCheques()) {
                    if(cheque.getCreatedAt().isEqual(chequeOneDayRestaurantRequest.getData())){
                        int usluga=cheque.getPriceAverage()*restaurant.getService()/100;
                        totalSumma+=usluga+cheque.getPriceAverage();
                        averageSumm=usluga/totalSumma;
                        numberCheque++;
                    }
                }
            }
        }

        return  ChequeOneDayRestaurantResponse.builder()
                .priceAverage(totalSumma)
                .numberCheque(numberCheque)

                .build();
    }

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
                .build();}}

