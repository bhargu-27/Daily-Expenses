package com.app.dailyexpenses;

class Expense {
    int amount;
    public String person,reason,date;
    public Expense(){}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Expense(int amount, String person, String reason, String date){
        this.amount=amount;
        this.person=person;
        this.reason=reason;
        this.date=date;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    public int getAmount() {
        return amount;
    }

    public String getPerson() {
        return person;
    }

    public String getReason() {
        return reason;
    }
    
}
