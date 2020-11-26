package Tables;

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
}
