package com.pms.model;

/**
 * Handles project billing. rate is per-hour and is set from the
 * owning Project subclass's calculateBillingRate().
 */
public class Billing {

    public enum Status {
        PENDING, PARTIAL, PAID
    }

    private double rate;
    private double hoursLogged;
    private double amountDue;
    private Status status = Status.PENDING;

    public Billing() {
    }

    public Billing(double rate) {
        this.rate = rate;
    }

    /** Logs additional hours worked and recalculates the amount due. */
    public void addHours(double hours) {
        this.hoursLogged += hours;
        recalculate();
    }

    private void recalculate() {
        this.amountDue = this.rate * this.hoursLogged;
        if (this.amountDue > 0 && this.status == Status.PENDING) {
            this.status = Status.PARTIAL;
        }
    }

    public void markPaid() {
        this.status = Status.PAID;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getHoursLogged() {
        return hoursLogged;
    }

    public void setHoursLogged(double hoursLogged) {
        this.hoursLogged = hoursLogged;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
