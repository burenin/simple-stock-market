package my.simple.stock.market;

import java.math.BigDecimal;

/**
 * @author andrejs.burenins
 *
 */
/**
 * @author andrejs.burenins
 *
 */
public final class Stock {
	
	
	
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
	
	public final static Stock TEA;
	public final static Stock POP;
	public final static Stock ALE;
	public final static Stock GIN;
	public final static Stock JOE;
	
	
	private final Symbol 	symbol;
	private final Type 		type;
	
	private BigDecimal 	lastDividend;
	private BigDecimal 	fixedDividend;
	private BigDecimal	parValue;
	
	static {
		TEA = new Stock(Symbol.TEA, Type.COMMON, BigDecimal.ZERO, null, new BigDecimal(100));
		POP = new Stock(Symbol.POP, Type.COMMON, new BigDecimal(8), null, new BigDecimal(100));
		ALE = new Stock(Symbol.ALE, Type.COMMON, new BigDecimal(23), null, new BigDecimal(60));
		GIN = new Stock(Symbol.GIN, Type.PREFERRED, new BigDecimal(8), new BigDecimal(2), new BigDecimal(100));
		JOE = new Stock(Symbol.JOE, Type.COMMON, new BigDecimal(13), null, new BigDecimal(250));
	}
	
	public Stock(final Symbol symbol, final Type type, BigDecimal lastDivident, BigDecimal fixedDivident, BigDecimal parValue) {
		this.symbol = symbol;
		this.type = type;
		this.lastDividend = lastDivident;
		this.fixedDividend = fixedDivident;
		this.parValue = parValue;
	}
	
	
	public Symbol getSymbol() {
		return symbol;
	}
	public Type getType() {
		return type;
	}
	
	public BigDecimal getLastDividend() {
		return lastDividend;
	}
	public void setLastDividend(BigDecimal lastDivident) {
		this.lastDividend = lastDivident;
	}
	public BigDecimal getFixedDividend() {
		return fixedDividend;
	}
	public void setFixedDividend(BigDecimal fixedDivident) {
		this.fixedDividend = fixedDivident;
	}
	
	public BigDecimal getParValue() {
		return parValue;
	}


	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}


	@Override
	public String toString() {
		return "Stock [symbol=" + symbol + ", type=" + type + ", lastDividend=" + lastDividend + ", fixedDividend="
				+ fixedDividend + ", parValue=" + parValue + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fixedDividend == null) ? 0 : fixedDividend.hashCode());
		result = prime * result + ((lastDividend == null) ? 0 : lastDividend.hashCode());
		result = prime * result + ((parValue == null) ? 0 : parValue.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		if (fixedDividend == null) {
			if (other.fixedDividend != null)
				return false;
		} else if (!fixedDividend.equals(other.fixedDividend))
			return false;
		if (lastDividend == null) {
			if (other.lastDividend != null)
				return false;
		} else if (!lastDividend.equals(other.lastDividend))
			return false;
		if (parValue == null) {
			if (other.parValue != null)
				return false;
		} else if (!parValue.equals(other.parValue))
			return false;
		if (symbol != other.symbol)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
	public static void main(String[] args) {
		double a = 8;
		int power = 3;
		double sum = Math.pow(a,(1.0/power));
		System.err.println(sum);
	}
}
