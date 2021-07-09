package lu.ftn.kpservice.converter;

import lu.ftn.kpservice.dto.ItemDto;
import lu.ftn.kpservice.dto.OrderDto;
import lu.ftn.kpservice.model.Item;
import lu.ftn.kpservice.model.CustomOrder;

import java.util.Set;
import java.util.stream.Collectors;

public class OrderConverter {

    public static CustomOrder toOrder(OrderDto dto){
        CustomOrder o = new CustomOrder();
        o.setId(dto.getId());
        o.setOrderUUID(dto.getOrderUUID());
        Set<Item> items = dto.getItems().stream().map(ItemConverter::toItem).collect(Collectors.toSet());
        o.setItems(items);
        o.setTotal(dto.getTotal());
        o.setSuccessUrl(dto.getSuccessUrl());
        o.setErrorUrl(dto.getErrorUrl());
        o.setTimeStamp(dto.getTimeStamp());
        o.setMerchantId(dto.getApiKey());
        return o;
    }
    public static OrderDto toDto(CustomOrder customOrder){
        OrderDto dto = new OrderDto();
        dto.setId(customOrder.getId());
        dto.setOrderUUID(customOrder.getOrderUUID());
        Set<ItemDto> items = customOrder.getItems().stream().map(ItemConverter::toDto).collect(Collectors.toSet());
        dto.setItems(items);
        dto.setTotal(customOrder.getTotal());
        dto.setSuccessUrl(customOrder.getSuccessUrl());
        dto.setErrorUrl(customOrder.getErrorUrl());
        dto.setTimeStamp(customOrder.getTimeStamp());
        dto.setApiKey(customOrder.getMerchantId());
        return dto;
    }
}
