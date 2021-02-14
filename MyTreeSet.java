import data.Ticket;

import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class MyTreeSet {
    private NavigableSet<Ticket> myTreeSet = new TreeSet<>();
    private int cntEvent = 0;

    void incrementEvent() {
        cntEvent++;
    }

    int getCntEvent() {
        return cntEvent;
    }

    void add(Ticket ticket) {
        myTreeSet.add(ticket);
    }

    void remove(Ticket ticket) {
        myTreeSet.remove(ticket);
    }

    boolean remove(int id) {
        for (Ticket t : myTreeSet) {
            if (t.getId() == id) {
                myTreeSet.remove(t);
                return true;
            }
        }
        return false;
    }

    void clear() {
        for (Ticket t : myTreeSet) {
            myTreeSet.remove(t);
        }
    }

    boolean isMax(Ticket ticket) {
        for (Ticket t : myTreeSet) {
            if (t.getPrice() > ticket.getPrice())
                return false;
        }
        return true;
    }

    boolean isMin(Ticket ticket) {
        for (Ticket t : myTreeSet) {
            if (t.getPrice() < ticket.getPrice())
                return false;
        }
        return true;
    }

    NavigableSet<Ticket> tailSet(Ticket ticket, boolean incl) {
        return myTreeSet.tailSet(ticket, incl);
    }

    int sumDiscount() {
        int sum = 0;
        for (Ticket t : myTreeSet) {
            sum += t.getDiscount();
        }
        return sum;
    }

    Ticket maxComment() {
        String max = "";
        Ticket ticket = null;
        for (Ticket t : myTreeSet) {
            if (t.getComment().compareTo(max) > 0) {
                max = t.getComment();
                ticket = t;
            }
        }
        return ticket;
    }

    void print() {
        for (Ticket ticket : myTreeSet) {
            System.out.println(ticket);
        }
    }

    Set<Float> uniquePrices() {
        float prevPrice = 0f;
        float price = 0f;
        float ppPrice = 0f;
        Set <Float> uniquePrices = new TreeSet<>();
        boolean first = true, second = true;
        for (Ticket t : myTreeSet) {
            if (first) {
                first = false;
                price = t.getPrice();
            } else {
                if (second) {
                    second = false;
                    prevPrice = price;
                    price = t.getPrice();
                    if (prevPrice != price){
                        uniquePrices.add(prevPrice);
                    }
                } else {
                    ppPrice = prevPrice;
                    prevPrice = price;
                    price = t.getPrice();
                    if (prevPrice != ppPrice && prevPrice != price){
                        uniquePrices.add(prevPrice);
                    }
                }
            }
        }
        if (price != prevPrice){
            uniquePrices.add(price);
        }
        return uniquePrices;
    }

    void showInfo() {
        System.out.println(myTreeSet);//??
    }

    int size() {
        return myTreeSet.size();
    }


}
