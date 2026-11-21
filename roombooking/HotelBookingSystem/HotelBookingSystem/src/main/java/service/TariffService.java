package service;

import model.Booking;

/** Tariff (bill) calculation. */
public class TariffService {

    public static final double GST_RATE = 0.12;        // 12% tax
    public static final int DISCOUNT_NIGHTS = 5;       // 5 or more nights ...
    public static final double DISCOUNT_RATE = 0.10;   // ... get 10% off

    public double baseAmount(double ratePerNight, int nights) {
        return ratePerNight * nights;
    }

    public double discount(double baseAmount, int nights) {
        return nights >= DISCOUNT_NIGHTS ? baseAmount * DISCOUNT_RATE : 0;
    }

    public double gst(double taxableAmount) {
        return taxableAmount * GST_RATE;
    }

    /** Calculates, prints and returns the final bill. */
    public double generateBill(Booking booking, int nights) {
        double rate = booking.getRoom().getRatePerNight();
        double base = baseAmount(rate, nights);
        double discount = discount(base, nights);
        double gst = gst(base - discount);
        double total = base - discount + gst;

        System.out.println("\n============ BILL ============");
        System.out.println("Guest        : " + booking.getGuest().getName());
        System.out.println("Room         : " + booking.getRoom().getNumber()
                + " (" + booking.getRoom().getType() + ")");
        System.out.printf("Nights       : %d x Rs.%.2f%n", nights, rate);
        System.out.printf("Base Amount  : Rs.%.2f%n", base);
        System.out.printf("Discount     : Rs.%.2f%n", discount);
        System.out.printf("GST (12%%)    : Rs.%.2f%n", gst);
        System.out.printf("TOTAL        : Rs.%.2f%n", total);
        System.out.println("==============================");
        return total;
    }
}
