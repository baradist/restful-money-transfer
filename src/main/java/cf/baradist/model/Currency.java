package cf.baradist.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "Account")
public class Currency {
    private int iso4217_code;
    private String iso4217_symcode;
    private String name;

    public Currency() {
    }

    public Currency(int iso4217_code, String iso4217_symcode, String name) {
        this.iso4217_code = iso4217_code;
        this.iso4217_symcode = iso4217_symcode;
        this.name = name;
    }

    public Currency(Currency that) {
        this.iso4217_code = that.iso4217_code;
        this.iso4217_symcode = that.iso4217_symcode;
        this.name = that.name;
    }

    @XmlElement(name = "iso4217_code")
    public int getIso4217_code() {
        return iso4217_code;
    }

    public void setIso4217_code(int iso4217_code) {
        this.iso4217_code = iso4217_code;
    }

    @XmlElement(name = "iso4217_symcode")
    public String getIso4217_symcode() {
        return iso4217_symcode;
    }

    public void setIso4217_symcode(String iso4217_symcode) {
        this.iso4217_symcode = iso4217_symcode;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return iso4217_code == currency.iso4217_code &&
                Objects.equals(iso4217_symcode, currency.iso4217_symcode) &&
                Objects.equals(name, currency.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iso4217_code, iso4217_symcode, name);
    }

    @Override
    public String toString() {
        return "Currency{" + iso4217_symcode + "(" + iso4217_code + ")}";
    }
}
