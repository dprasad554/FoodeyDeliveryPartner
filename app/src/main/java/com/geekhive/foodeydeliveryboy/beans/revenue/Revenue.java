
package com.geekhive.foodeydeliveryboy.beans.revenue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Revenue {

    @SerializedName("today_complete")
    @Expose
    private double todayComplete;
    @SerializedName("today_cancel")
    @Expose
    private double todayCancel;
    @SerializedName("today_sum")
    @Expose
    private double todaySum;
    @SerializedName("today_sum_order")
    @Expose
    private int todaySumOrder;
    @SerializedName("week_complete")
    @Expose
    private double weekComplete;
    @SerializedName("week_cancel")
    @Expose
    private double weekCancel;
    @SerializedName("week_sum")
    @Expose
    private double weekSum;
    @SerializedName("week_sum_order")
    @Expose
    private int weekSumOrder;
    @SerializedName("month_complete")
    @Expose
    private double monthComplete;
    @SerializedName("month_cancel")
    @Expose
    private double monthCancel;
    @SerializedName("month_sum")
    @Expose
    private double monthSum;
    @SerializedName("month_sum_order")
    @Expose
    private int monthSumOrder;
    @SerializedName("year_complete")
    @Expose
    private double yearComplete;
    @SerializedName("year_cancel")
    @Expose
    private double yearCancel;
    @SerializedName("year_sum")
    @Expose
    private double yearSum;
    @SerializedName("year_sum_order")
    @Expose
    private int yearSumOrder;

    public double getTodayComplete() {
        return todayComplete;
    }

    public void setTodayComplete(double todayComplete) {
        this.todayComplete = todayComplete;
    }

    public double getTodayCancel() {
        return todayCancel;
    }

    public void setTodayCancel(double todayCancel) {
        this.todayCancel = todayCancel;
    }

    public double getTodaySum() {
        return todaySum;
    }

    public void setTodaySum(double todaySum) {
        this.todaySum = todaySum;
    }

    public int getTodaySumOrder() {
        return todaySumOrder;
    }

    public void setTodaySumOrder(int todaySumOrder) {
        this.todaySumOrder = todaySumOrder;
    }

    public double getWeekComplete() {
        return weekComplete;
    }

    public void setWeekComplete(double weekComplete) {
        this.weekComplete = weekComplete;
    }

    public double getWeekCancel() {
        return weekCancel;
    }

    public void setWeekCancel(double weekCancel) {
        this.weekCancel = weekCancel;
    }

    public double getWeekSum() {
        return weekSum;
    }

    public void setWeekSum(double weekSum) {
        this.weekSum = weekSum;
    }

    public int getWeekSumOrder() {
        return weekSumOrder;
    }

    public void setWeekSumOrder(int weekSumOrder) {
        this.weekSumOrder = weekSumOrder;
    }

    public double getMonthComplete() {
        return monthComplete;
    }

    public void setMonthComplete(double monthComplete) {
        this.monthComplete = monthComplete;
    }

    public double getMonthCancel() {
        return monthCancel;
    }

    public void setMonthCancel(double monthCancel) {
        this.monthCancel = monthCancel;
    }

    public double getMonthSum() {
        return monthSum;
    }

    public void setMonthSum(double monthSum) {
        this.monthSum = monthSum;
    }

    public int getMonthSumOrder() {
        return monthSumOrder;
    }

    public void setMonthSumOrder(int monthSumOrder) {
        this.monthSumOrder = monthSumOrder;
    }

    public double getYearComplete() {
        return yearComplete;
    }

    public void setYearComplete(double yearComplete) {
        this.yearComplete = yearComplete;
    }

    public double getYearCancel() {
        return yearCancel;
    }

    public void setYearCancel(double yearCancel) {
        this.yearCancel = yearCancel;
    }

    public double getYearSum() {
        return yearSum;
    }

    public void setYearSum(double yearSum) {
        this.yearSum = yearSum;
    }

    public int getYearSumOrder() {
        return yearSumOrder;
    }

    public void setYearSumOrder(int yearSumOrder) {
        this.yearSumOrder = yearSumOrder;
    }

}
