package com.tambunan.messages;

import com.tambunan.bus.BuzzCommand;

public class CalculatePayroll implements BuzzCommand {
    private String employeeId;

    public CalculatePayroll(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
