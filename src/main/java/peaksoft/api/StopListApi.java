package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.StopListResponse;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.service.StopListService;


@RestController
@RequestMapping("/api/stopLists")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN','CHIEF','WAITER')")
public class StopListApi {
    private final StopListService service;


    @PostMapping
    public SimpleResponse saveStopList(@RequestBody StopListRequest stopListRequest){
        return service.saveStopList(stopListRequest);
    }

    @GetMapping("/{id}")

    public StopListResponse findById(@PathVariable Long id){
        return service.getStopListById(id);
    }
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public SimpleResponse deleteStopListById(@PathVariable Long id){
        return service.deleteStopListById(id);
    }
    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public SimpleResponse updateStopListById(@PathVariable Long id , @RequestBody StopListRequest stopListRequest){
        return service.updateStopList(id,stopListRequest);
    }

}
