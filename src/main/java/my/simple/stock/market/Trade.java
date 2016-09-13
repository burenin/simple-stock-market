package my.simple.stock.market;

public class Trade {
	
	enum TradeSide {
		BUY,
		SELL
	}
	
	private long timestamp;
	private long quantity;
	private TradeSide side;
	private int price;
	
	
}
