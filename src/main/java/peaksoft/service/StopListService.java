package peaksoft.service;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.StopListResponse;
import peaksoft.dto.response.pagination.StopListResponsePagination;
import peaksoft.dto.simple.SimpleResponse;

import java.util.List;

public interface StopListService {


    SimpleResponse saveStopList(StopListRequest stopListRequest);

    StopListResponse getStopListById(Long stopListId);
    SimpleResponse updateStopList(Long stopListId, StopListRequest stopListRequest);
    SimpleResponse deleteStopListById(Long stopListId);

}
