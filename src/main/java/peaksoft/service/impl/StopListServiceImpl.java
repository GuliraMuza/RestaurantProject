package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.StopListResponse;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.service.StopListService;

@Service
@Transactional
@RequiredArgsConstructor
public class StopListServiceImpl  implements StopListService {

    private final MenuItemRepository menuItemRepository;
    private final StopListRepository stopListRepository;


    @Override
    public SimpleResponse saveStopList(StopListRequest stopListRequest) {
        MenuItem menuItem1 = menuItemRepository.findById(stopListRequest.getMenuItemId()).orElseThrow(
                () -> new NotFoundException("StopList with id: " + stopListRequest.getMenuItemId()));
        if (stopListRepository.count(stopListRequest.getDate(),menuItem1.getName()) > 0) {
            throw new AlreadyExistException("This StopList already exists");
        } else {
            StopList stopList = new StopList();
            stopList.setReason(stopListRequest.getReason());
            stopList.setDate(stopListRequest.getDate());
            stopList.setMenuItem(menuItem1);
            stopListRepository.save(stopList);
        }

        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("With one date once only saved!")
                .build();
    }


    @Override
    public StopListResponse getStopListById(Long stopListId) {
        return stopListRepository.getStopListById(stopListId).orElseThrow(()->new NotFoundException("not found"));
    }

    public SimpleResponse updateStopList(Long id, StopListRequest stopListRequest) {
        MenuItem menuItem1 = menuItemRepository.findById(stopListRequest.getMenuItemId()).orElseThrow(
                () -> new NotFoundException("StopList with id: " + stopListRequest.getMenuItemId()));
        StopList stopList = stopListRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("StopList with id" + id + "doesn't exists")));
        if (stopListRepository.count(stopListRequest.getDate(),menuItem1.getName()) > 0) {
            throw new AlreadyExistException("This StopList already exists");
        } else {
            stopList.setReason(stopListRequest.getReason());
            stopList.setDate(stopListRequest.getDate());
            stopListRepository.save(stopList);
        }
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteStopListById(Long stopListId) {
            stopListRepository.deleteById(stopListId);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("deleted")
                    .build();
        }
    }



