package my.simple.stock.market;

import java.math.BigDecimal;

/**
 * @author andrejs.burenins
 *
 */
public class Trade implements Comparable<Trade>{
	
	enum TradeSide {
		BUY,
		SELL
	}
	
	private long 		timestamp;
	private Stock 		stock;
	private long 		quantity;
	private TradeSide 	side;
	private BigDecimal 	price;
	
	public Trade(long timestamp, Stock stock, long quantity, TradeSide side, BigDecimal price) {
		this.timestamp = timestamp;
		this.stock = stock;
		this.quantity = quantity;
		this.side = side;
		this.price = price;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public TradeSide getSide() {
		return side;
	}

	public void setSide(TradeSide side) {
		this.side = side;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public int compareTo(Trade o) {
		return Long.compare(timestamp, o.timestamp);
	}

	@Override
	public String toString() {
		return "Trade [timestamp=" + timestamp + ", stock=" + stock.getSymbol() + ", quantity=" + quantity + ", side=" + side
				+ ", price=" + price + "]";
	}
}
