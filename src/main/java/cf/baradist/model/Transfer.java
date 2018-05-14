package cf.baradist.model;

import java.math.BigDecimal;

public class Transfer {
    private int currencyCode;
    private BigDecimal amount;
    private Long fromAccountId;
    private Long toAccountId;

    public Transfer() {
    }

    public Transfer(int currencyCode, BigDecimal amount, Long fromAccountId, Long toAccountId) {
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
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
        return currencyCode == transfer.currencyCode &&
                amount.equals(transfer.amount) &&
                fromAccountId.equals(transfer.fromAccountId) &&
                toAccountId.equals(transfer.toAccountId);
    }

    @Override
    public int hashCode() {
        int result = currencyCode;
        result = 31 * result + amount.hashCode();
        result = 31 * result + fromAccountId.hashCode();
        result = 31 * result + toAccountId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
                ", currencyCode=" + currencyCode +
                ", amount=" + amount +
                '}';
    }
}
