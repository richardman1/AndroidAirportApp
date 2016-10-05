package rjobse.mysqliteexample;

import java.io.Serializable;

/**
 * Created by devc0 on 9/13/2016.
 */
public class Airport implements Serializable {
    private String icao;
    private String name;
    private String iso_country;

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso_country() { return iso_country; }

    public void setIso_country(String iso_country) { this.iso_country = iso_country; }
}
