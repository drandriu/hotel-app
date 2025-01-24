package com.example.hotel.hotelapp.dtos;

import java.util.List;

import lombok.Data;


@Data
public class DynamicSearchDTO {
    
    private List<SearchCriteria> listSearchCriteria;
    private List<OrderCriteria> listOrderCriteria;
    private PageRequest page;

    @Data
    public static class SearchCriteria {
        private String key;
        private String value;
        private String operation;  // equals, lower, higher...
    }

    @Data
    public static class OrderCriteria {
        private String sortBy;
        private String valuesortOrder;  // ASC, DESC
    }

    @Data
    public static class PageRequest {
        private int pageIndex;
        private int pageSize;
    }
}
