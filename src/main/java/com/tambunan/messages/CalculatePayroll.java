package com.tambunan.messages;

import com.tambunan.bus.BuzzMessage;

public class CalculatePayroll implements BuzzMessage {
    private String employeeId;

    public CalculatePayroll(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
