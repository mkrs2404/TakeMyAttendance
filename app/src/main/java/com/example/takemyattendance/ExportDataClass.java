package com.example.takemyattendance;

public class ExportDataClass extends StudentData {
    private int attendedPeriods;
    private int totalPeriods;
    private String percentageAttended;

    public ExportDataClass(String roll, String name, String phone, String email, String parent_phone, int attendedPeriods) {
        super(roll, name, phone, email, parent_phone);
        this.attendedPeriods = attendedPeriods;
    }

    public int getAttendedPeriods() {
        return attendedPeriods;
    }

    public void setAttendedPeriods(int attendedPeriods) {
        this.attendedPeriods = attendedPeriods;
    }

    public int getTotalPeriods() {
        return totalPeriods;
    }

    public void setTotalPeriods(int totalPeriods) {
        this.totalPeriods = totalPeriods;
    }

    public String getPercentageAttended() {
        return percentageAttended;
    }

    public void setPercentageAttended(String percentageAttended) {
        this.percentageAttended = percentageAttended;
    }

    @Override
    public String toString() {
        return "ExportDataClass{" +
                "roll=" + getRoll() +
                "name=" + getName() +
                "attendedClasses=" + attendedPeriods +
                ", totalClasses=" + totalPeriods +
                ", percentageAttended='" + percentageAttended + '\'' +
                '}';
    }
}
