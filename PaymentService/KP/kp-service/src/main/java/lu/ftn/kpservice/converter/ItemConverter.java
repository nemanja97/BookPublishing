package lu.ftn.kpservice.converter;

import lu.ftn.kpservice.dto.ItemDto;
import lu.ftn.kpservice.model.Item;

public class ItemConverter {

    public static Item toItem(ItemDto dto){
        Item i = new Item();
        i.setId(dto.getId());
        i.setName(dto.getName());
        i.setNumberOfItems(dto.getNumberOfItems());
        i.setUnitPrice(dto.getUnitPrice());
        return i;
    }

    public static ItemDto toDto(Item item){
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setNumberOfItems(item.getNumberOfItems());
        dto.setUnitPrice(item.getUnitPrice());
        return dto;
    }
}
