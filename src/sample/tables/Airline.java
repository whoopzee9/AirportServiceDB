package sample.tables;

import java.sql.Date;

public class Airline {
    private String name;
    private Date foundationDate;

    public Airline(String name, Date foundationDate) {
        this.name = name;
        this.foundationDate = foundationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(Date foundationDate) {
        this.foundationDate = foundationDate;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Airline airline = (Airline) obj;

        return airline.name.equals(this.name) && airline.foundationDate.equals(this.foundationDate);
    }
}
