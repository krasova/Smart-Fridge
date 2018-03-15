package com.smartfridge.manager.service;

import com.smartfridge.manager.dto.FridgeItem;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

import static lombok.Lombok.checkNotNull;

@Slf4j
public class SmartFridgeManagerImpl implements SmartFridgeManager {

  private List<FridgeItem> fridgeItemList;

  private SmartFridgeManagerImpl(){
    this.fridgeItemList = new ArrayList<>();
  }

  private SmartFridgeManagerImpl(List<FridgeItem> fridgeItemList){
    this.fridgeItemList = new ArrayList<>();
  }

  @Override
  public void handleItemRemoved(String itemUUID) {
    fridgeItemList.stream()
        .filter(fridgeItem -> fridgeItem.getItemUUID().equals(itemUUID)).findFirst()
        .map(fridgeItemForRemove -> fridgeItemList.remove(fridgeItemForRemove));
    sendMessage(itemUUID);
  }

  @Override
  public void handleItemAdded(long itemType, String itemUUID, String name, Double fillFactor) {
    checkNotNull(itemType, "Item type is required!");
    checkNotNull(itemUUID, "Item UUID is required!");
    checkNotNull(name, "Item name is required!");
    checkNotNull(fillFactor, "FillFactor is required!");
    FridgeItem fridgeItem = FridgeItem.builder().itemType(itemType).itemUUID(itemUUID).name(name)
        .fillFactor(fillFactor).forgetFlag(false).build();
    fridgeItemList.add(fridgeItem);
    sendMessage(itemUUID);
  }

  @Override
  public Object[] getItems(Double fillFactor) {
    return fridgeItemList.stream()
        .filter(fridgeItem -> !fridgeItem.isForgetFlag())
        .filter(fridgeItem -> fridgeItem.getFillFactor() <= fillFactor).collect(Collectors.toList())
        .toArray();
//    Map<Long, Set<Double>> itemsByItemType = fridge.getFridgeItemList().stream()
//        .filter(fridgeItem -> fridgeItem.isForgetFlag() == false)
//        .filter(fridgeItem -> fridgeItem.getFillFactor() <= fillFactor).collect(Collectors
//            .groupingBy(FridgeItem::getItemType,
//                Collectors.mapping(FridgeItem::getFillFactor, Collectors.toSet())));
  }

  @Override
  public Double getFillFactor(long itemType) {
    OptionalDouble fillFactor = fridgeItemList.stream()
        .filter(fridgeItem -> fridgeItem.getItemType().equals(itemType))
        .mapToDouble(FridgeItem::getFillFactor).filter(f -> f > 0.0d).average();
    if (fillFactor.isPresent()) {
      return fillFactor.getAsDouble();
    }
    return 0.0d;
  }

  @Override
  public void forgetItem(long itemType) {
    fridgeItemList.stream()
        .filter(fridgeItem -> fridgeItem.getItemType().equals(itemType))
        .forEach(fridgeItem -> fridgeItem.setForgetFlag(true));
  }

  private void sendMessage(String itemUUID) {
    //TODO Implement send message functionality
  }
}
