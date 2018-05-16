package cf.baradist.model;

import java.math.BigDecimal;
import java.util.Objects;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(int currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

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
