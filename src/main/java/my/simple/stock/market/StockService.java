package my.simple.stock.market;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.DoubleConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import my.simple.stock.market.Stock.Type;
import my.simple.stock.market.Trade.TradeSide;

/**
 * @author andrejs.burenins
 *
 */
public class StockService implements IStockService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);
	
	private Map<Stock, List<Trade>> tradesLookup = new ConcurrentHashMap<>();
	
	@Override
	public BigDecimal calculateDividendYield(final Stock stock, BigDecimal price) {
		BigDecimal dividentYield = null;
		if ((price != null) && BigDecimal.ZERO.compareTo(price) != 0) {
			if (Type.COMMON == stock.getType()) {
				BigDecimal lastDividend = stock.getLastDividend();
				dividentYield = divide(lastDividend, price);
			} else if (Type.PREFERRED == stock.getType()) {
				BigDecimal fixedDividend = stock.getFixedDividend();
				BigDecimal parValue = stock.getParValue();
				dividentYield = fixedDividend.multiply(parValue);
				dividentYield = divide(dividentYield, price);
			}
		}
		return round(dividentYield);
	}
	
	@Override
	public BigDecimal calculatePERatio(final Stock stock, BigDecimal price) {
		BigDecimal peRatio = null;
		BigDecimal divident = calculateDividendYield(stock, price);
		if (divident != null) {
			peRatio = divide(price, divident);
		}
		return round(peRatio);
	}
	
	@Override
	public BigDecimal calculateVolumeWeightedPrice(Stock stock) {
		BigDecimal result = null;
		List<Trade> trades = findTrades(stock);
		if (!trades.isEmpty()) {
			Collections.sort(trades, Collections.reverseOrder());
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, -15);
			long minimumTimestamp = calendar.getTime().getTime();
			
			BigDecimal totalNumerator = BigDecimal.ZERO;
			BigDecimal totalDenominator = BigDecimal.ZERO;
			
			// use Java 7 apprach			
			for (Trade trade : trades) {
				if (trade.getTimestamp() < minimumTimestamp) {
					break;
				}
				BigDecimal price = trade.getPrice();
				BigDecimal quantity = new BigDecimal(trade.getQuantity());
				totalNumerator = totalNumerator.add(price.multiply(quantity));
				totalDenominator = totalDenominator.add(quantity);
			}
			
			result = divide(totalNumerator, totalDenominator);
		}
		return round (result);
	}

	
	@Override
	public BigDecimal calculateGBCEAllShareIndex() {
		
		// use Java 8 Stream / piplines / reduction / collection operations
		
		GeometricMean geometricMean = null;
		synchronized (tradesLookup) {
			geometricMean = tradesLookup
					.values()																			// Collection <List<Trade>>
					.stream()																			// Stream <List<Trade>>
					.flatMap(t -> t.stream())   														// Stream <Trade>
					.map(Trade::getPrice) 																// Trade's price (BigDecimal)
					.map(p -> p.doubleValue())  														// Trade's price (double)
					.collect(GeometricMean::new, GeometricMean::accept, GeometricMean::combine);		// GeometricMean (as supplier, accumulator and combiner)
					
		}
		
		return round (geometricMean.geometricMean());
	}

	@Override
	public Trade executeTrade(final Stock stock, int quantity, TradeSide side, BigDecimal price) {
		Trade trade = new Trade(System.currentTimeMillis(), stock, quantity, side, price);
		synchronized (tradesLookup) {
			List<Trade> trades = tradesLookup.get(stock);
			if (trades == null) {
				trades = new ArrayList<>();
				tradesLookup.put(stock, trades);
			}
			trades.add(trade);
		}
		return trade;
	}
	
	
	@Override
	public List<Trade> findTrades(Stock stock) {
		List<Trade> trades = new ArrayList<>();
		synchronized (tradesLookup) {
			List<Trade> recordedTrades = tradesLookup.get(stock);
			if (recordedTrades != null) {
				trades = new ArrayList<>(recordedTrades);
			}
		}
		return trades;
	}

	private BigDecimal divide(BigDecimal numerator, BigDecimal divisor ) {
		return numerator.divide(divisor, 5, RoundingMode.HALF_EVEN);
	}
	
	private BigDecimal round (BigDecimal decimal) {
		BigDecimal result = decimal;
		if (decimal != null) {
			result = decimal.setScale(5, RoundingMode.HALF_EVEN);
		}
		return result;
	}

	public static void main(String[] args) {
		Map<Stock, List<Trade>> lookup = new HashMap<>();
		
		List<Trade> trades = new ArrayList<>();
		trades.add(new Trade(0, null, 0, null, new BigDecimal(1)));
		trades.add(new Trade(0, null, 0, null, new BigDecimal(3)));
		trades.add(new Trade(0, null, 0, null, new BigDecimal(9)));
		lookup.put(Stock.ALE, trades);
		
		List<Trade> trades2 = new ArrayList<>();
		trades2.add(new Trade(0, null, 0, null, new BigDecimal(5)));
		lookup.put(Stock.GIN, trades2);
		
		List<Trade> trades3 = new ArrayList<>();
		trades3.add(new Trade(0, null, 0, null, new BigDecimal(10)));
		lookup.put(Stock.JOE, trades3);
		
		BigDecimal multiple = trades
		.stream()
		.map(Trade::getPrice)
		.reduce(BigDecimal.ONE, (p1, p2) -> p1.multiply(p2));
		
		System.err.println(multiple);
		
		GeometricMean gm = trades
				.stream()
				.map(Trade::getPrice)
				.map(p -> p.doubleValue())
				.collect(GeometricMean::new, GeometricMean::accept, GeometricMean::combine);
		System.err.println(gm.geometricMean());
		
		GeometricMean gm2 = lookup.values().stream().flatMap(t -> t.stream()).map(Trade::getPrice)
		.map(p -> p.doubleValue())
		.collect(GeometricMean::new, GeometricMean::accept, GeometricMean::combine);
		System.err.println(gm2.geometricMean());
	}
	
	static class GeometricMean implements DoubleConsumer {
		
		private double product = 1;
	    private int count = 0;
	    
	    public BigDecimal geometricMean() {
	    	double geometricMean = 1;
	    	if (count > 0){
	    		geometricMean =  Math.pow(product,(1.0/count));
	    	}
	        return new BigDecimal(geometricMean);
	    }

		@Override
		public void accept(double value) {
			product *= value;
			count++;
		}
		
		public void combine(GeometricMean other) {
			product *= other.product;
	        count += other.count;
	    }
	}
}
