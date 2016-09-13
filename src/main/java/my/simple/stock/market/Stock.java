package my.simple.stock.market;

/**
 * @author andrejs.burenins
 *
 */
public class Stock {
	
	enum Symbol {
		TEA,
		POP,
		ALE,
		GIN,
		JOE
	}
	
	enum Type {
		COMMON,
		PREFERRED
	}
	
	private final Symbol 	symbol;
	private final Type 		type;
	
	private int 	lastDividend;
	private int 	fixedDividend;
	private int		parValue;
	private int 	price;
	
	
	
	public Stock(final Symbol symbol, final Type type) {
		this.symbol = symbol;
		this.type = type;
	}
	
	
	public Symbol getSymbol() {
		return symbol;
	}
	public Type getType() {
		return type;
	}
	
	public int getLastDividend() {
		return lastDividend;
	}
	public void setLastDividend(int lastDivident) {
		this.lastDividend = lastDivident;
	}
	public int getFixedDividend() {
		return fixedDividend;
	}
	public void setFixedDividend(int fixedDivident) {
		this.fixedDividend = fixedDivident;
	}
	
	public int getParValue() {
		return parValue;
	}


	public void setParValue(int parValue) {
		this.parValue = parValue;
	}


	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "Stock [symbol=" + symbol + ", type=" + type + ", lastDivident=" + lastDividend + ", fixedDivident="
				+ fixedDividend + ", price=" + price + "]";
	}
}
