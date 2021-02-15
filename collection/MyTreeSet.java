package collection;

import data.Ticket;

import java.util.ConcurrentModificationException;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class MyTreeSet {
    private NavigableSet<Ticket> myTreeSet = new TreeSet<>();
    private int cntEvent = 0;

    public void incrementEvent() {
        cntEvent++;
    }

    public int getCntEvent() {
        return cntEvent;
    }

    public void add(Ticket ticket) {
        myTreeSet.add(ticket);
    }

    public void remove(Ticket ticket) {
        myTreeSet.remove(ticket);
    }

    public boolean remove(int id) {
        Ticket ticket = null;
        for (Ticket t : myTreeSet) {
            if (t.getId() == id) {
                ticket = t;
            }
        }
        if (ticket == null){
            return false;
        }else{
            myTreeSet.remove(ticket);
            return true;
        }
    }

    public void removeAll(NavigableSet<Ticket> set){
        myTreeSet.removeAll(set);
    }

    public void clear() {
        myTreeSet.clear();
    }

    public boolean isMax(Ticket ticket) {
        for (Ticket t : myTreeSet) {
            if (t.getPrice() > ticket.getPrice())
                return false;
        }
        return true;
    }

    public boolean isMin(Ticket ticket) {
        for (Ticket t : myTreeSet) {
            if (t.getPrice() < ticket.getPrice())
                return false;
        }
        return true;
    }

    public void headSet(Ticket ticket, boolean incl) {
        myTreeSet =  myTreeSet.headSet(ticket, incl);
    }

    public int sumDiscount() {
        int sum = 0;
        for (Ticket t : myTreeSet) {
            sum += t.getDiscount();
        }
        return sum;
    }

    public Ticket maxComment() {
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

    public void print() {
        for (Ticket ticket : myTreeSet) {
            System.out.println(ticket);
        }
    }

    public Set<Float> uniquePrices() {
        float prevPrice = 0f;
        float price = 0f;
        float ppPrice = 0f;
        Set <Float> uniquePrices = new TreeSet<>();
        if (myTreeSet.size() == 1){
            uniquePrices.add(myTreeSet.first().getPrice());
            return uniquePrices;
        }
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

    public void showInfo() {
        System.out.println(myTreeSet);//??
    }

    public int size() {
        return myTreeSet.size();
    }


}
