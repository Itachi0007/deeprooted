package DemandSupply;
import java.time.LocalDateTime;
import java.util.*;

public class DemandSupplyClass {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
    }

    public static String trade(PriorityQueue<ItemModel> pqSupply, PriorityQueue<ItemModel> pqDemand) {
        // logic to sell items

        while (!pqSupply.isEmpty() && !pqDemand.isEmpty()) {
            ItemModel sold = pqSupply.peek();
            System.out.println("Seller price -> " + sold.getPrice());
            ItemModel bought = pqDemand.peek();
            if(sold.getPrice() < bought.getPrice() && sold.getTimestamp().isBefore(bought.getTimestamp())) {
                // trade happens for quantity ->
                Integer quantity = Math.min(sold.getQuantity(), bought.getQuantity());
                bought.setQuantity(bought.getQuantity() - quantity);
                sold.setQuantity(sold.getQuantity() - quantity);

                System.out.println(
                        "d" + bought.getId() + " " + "s" + sold.getId() + " " + sold.getPrice() + "/kg" + " "
                                + quantity + "kg");
            } else {
                // find next cheaper supply
                Queue<ItemModel> queue = new LinkedList<>();
                while(!pqSupply.isEmpty() && pqSupply.peek().getPrice() < pqDemand.peek().getPrice()) {
                    queue.add(pqSupply.remove());
                    System.out.println(pqSupply.peek().getId());
                }

                if(queue == null || queue.isEmpty()) {
                    System.out.println("Everything is too costly");
                    return "Everything is too costly";
                }

                ItemModel sold2 = pqSupply.peek();
                ItemModel bought2 = pqDemand.peek();
                // trade happens for quantity ->
                Integer quantity = Math.min(sold2.getQuantity(), bought2.getQuantity());
                bought2.setQuantity(bought2.getQuantity() - quantity);
                sold2.setQuantity(sold2.getQuantity() - quantity);

                System.out.println(
                        "d" + bought2.getId() + " " + "s" + sold2.getId() + " " + sold2.getPrice() + "/kg" + " "
                                + quantity + "kg");

                while(!queue.isEmpty()) {
                    pqSupply.add(queue.remove());
                }
            }

            // update the quantities accordingly
            if(sold.getQuantity() == 0) {
                pqSupply.remove();
                System.out.println("Sold");
            } else {
                pqSupply.peek().setQuantity(sold.getQuantity());
                System.out.println("Supply -> " +pqSupply.peek().getQuantity());
            }

            if(bought.getQuantity() == 0) {
                pqDemand.remove();
                System.out.println("Bought");
            } else {
                pqDemand.peek().setQuantity(bought.getQuantity());
                System.out.println("Demand -> " + pqDemand.peek().getQuantity());
            }
        }
        return "Trade completed";
    }

    static class SellerComparator implements Comparator<ItemModel> {

        // Overriding compare()method of Comparator
        // for descending order of cgpa
        public int compare(ItemModel item1, ItemModel item2) {
            if (item1.getPrice() < item2.getPrice())
                return -1;
            else if (item1.getPrice() > item2.getPrice())
                return 1;
            else if(item1.getTimestamp().isBefore(item2.getTimestamp()))
                return -1;
            return 0;
        }
    }

    static class BuyerComparator implements Comparator<ItemModel> {

        // Overriding compare()method of Comparator
        // for descending order of cgpa
        public int compare(ItemModel item1, ItemModel item2) {
            if (item1.getPrice() < item2.getPrice())
                return 1;
            else if (item1.getPrice() > item2.getPrice())
                return -1;
            else if(item1.getTimestamp().isBefore(item2.getTimestamp()))
                return -1;
            return 0;
        }
    }

}


