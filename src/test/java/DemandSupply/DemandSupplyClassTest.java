package DemandSupply;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.PriorityQueue;

class DemandSupplyClassTest {

    private DemandSupplyClass demandSupplyClass;
    PriorityQueue<ItemModel>pqSupply = new PriorityQueue<ItemModel>(new DemandSupplyClass.SellerComparator());
    PriorityQueue<ItemModel>pqDemand = new PriorityQueue<ItemModel>(new DemandSupplyClass.BuyerComparator());

    @BeforeAll
    public static void createAllPQs() {
        System.out.println("Running trades");
    }

    @Test
    @DisplayName("Test 1")
    public void test1() {


        LocalDateTime timeNow = LocalDateTime.now();
        ItemModel s1 = new ItemModel(1, "tomato",24, timeNow.minusHours(3), 100);
        ItemModel s2 = new ItemModel(2, "tomato",20, timeNow.minusHours(2), 90);
        ItemModel d1 = new ItemModel(1, "tomato",22, timeNow.minusHours(3), 110);
        ItemModel d2 = new ItemModel(2, "tomato",21, timeNow.minusHours(2), 10);
        ItemModel d3 = new ItemModel(3, "tomato",21, timeNow.minusHours(1), 40);
        ItemModel s3 = new ItemModel(3, "tomato",19, timeNow.minusHours(1), 50);

        pqSupply.add(s1);
        DemandSupplyClass.trade(pqSupply, pqDemand);
        pqSupply.add(s2);
        DemandSupplyClass.trade(pqSupply, pqDemand);
        pqDemand.add(d1);
        DemandSupplyClass.trade(pqSupply, pqDemand);
        pqDemand.add(d2);
        DemandSupplyClass.trade(pqSupply, pqDemand);
        pqSupply.add(s3);
        DemandSupplyClass.trade(pqSupply, pqDemand);
        pqDemand.add(d3);
        DemandSupplyClass.trade(pqSupply, pqDemand);


    }
}