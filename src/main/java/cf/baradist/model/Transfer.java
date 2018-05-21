package cf.baradist.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Objects;

@XmlRootElement(name = "Transfer")
public class Transfer {
    private Long id;
    private int currencyCode;
    private BigDecimal amount;
    private Long fromAccountId;
    private Long toAccountId;

    public Transfer() {
    }

    public Transfer(Long id, int currencyCode, BigDecimal amount, Long fromAccountId, Long toAccountId) {
        this.id = id;
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
    }

    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "currencyCode")
    public int getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(int currencyCode) {
        this.currencyCode = currencyCode;
    }

    @XmlElement(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @XmlElement(name = "fromAccountId")
    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    @XmlElement(name = "toAccountId")
    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transfer transfer = (Transfer) o;
        return id.equals(transfer.id) &&
                currencyCode == transfer.currencyCode &&
                amount.equals(transfer.amount) &&
                fromAccountId.equals(transfer.fromAccountId) &&
                toAccountId.equals(transfer.toAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyCode, amount, fromAccountId, toAccountId);
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
                ", currencyCode=" + currencyCode +
                ", amount=" + amount +
                '}';
    }
}
