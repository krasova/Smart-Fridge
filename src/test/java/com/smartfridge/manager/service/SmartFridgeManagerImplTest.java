package com.smartfridge.manager.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

import com.smartfridge.manager.dto.FridgeItem;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class SmartFridgeManagerImplTest {

  @InjectMocks
  SmartFridgeManagerImpl tested;

  List<FridgeItem> fridgeItemList = new ArrayList<>();

  @Before
  public void init(){

    FridgeItem fridgeItem1 = FridgeItem.builder().name("test1").itemType(1L).itemUUID("1").fillFactor(0.5d).forgetFlag(false).build();
    FridgeItem fridgeItem2 = FridgeItem.builder().name("test2").itemType(1L).itemUUID("1").fillFactor(0.6d).forgetFlag(false).build();
    fridgeItemList.add(fridgeItem1);
    fridgeItemList.add(fridgeItem2);
    ReflectionTestUtils.setField(tested, "fridgeItemList", fridgeItemList);
  }


  @Test
  public void handleItemRemoved() throws Exception {
    //setup
    FridgeItem fridgeItem1 = FridgeItem.builder().name("test1").itemType(1L).itemUUID("1").fillFactor(0.5d).forgetFlag(false).build();
    //execute
    tested.handleItemRemoved("1");
    //verify
    assertThat(fridgeItemList.contains(fridgeItem1),is(false));
  }

  @Test(expected = NullPointerException.class)
  public void handleItemAdded_NullPointerException() throws Exception {
    // setup
    // execute
    tested.handleItemAdded(1, null, null, null);
    // verify
    fail();
  }

  @Test
  public void handleItemAdded() throws Exception {
    // setup
    FridgeItem fridgeItem = FridgeItem.builder().name("test").itemType(1L).itemUUID("3").fillFactor(0.2d).forgetFlag(false).build();
    // execute
    tested.handleItemAdded(1L, "3", "test", 0.2d);
    // verify
    assertThat(fridgeItemList.contains(fridgeItem),is(true));
  }

  @Test
  public void getItems() throws Exception {
    // setup
    // execute
    Object[] results = tested.getItems(0.5d);
    // verify
    assertThat(results.length,is(1));
  }

  @Test
  public void getFillFactor() throws Exception {
    // setup
    // execute
    Double result = tested.getFillFactor(1);
    // verify
    assertThat(result,is(0.55d));
  }

  @Test
  public void forgetItem() throws Exception {
    // setup
    // execute
    tested.forgetItem(1);
    // verify
    assertThat(fridgeItemList.get(0).isForgetFlag(),is(true));
  }

}