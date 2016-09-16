package my.simple.stock.market;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import my.simple.stock.market.Stock.Symbol;
import my.simple.stock.market.Trade.TradeSide;

/**
 * <ol type="a">
 * 	<li> For a given stock :
 * 		<ol type="i">
 * 			<li> Given price, calculates the dividend yield
 * 			<li> Given price, calculates the P/E Ratio
 * 			<li> Records a trade, with timestamp, quantity of shares, buy or sell indicator and traded price
 * 			<li> Calculates Volume Weighted Stock Price based on trades in past 15 minutes
 * 		</ol>
 * 	<li> GBCE All Share Index using the geometric mean of prices for all stocks
 * </ol>
 * <b>NOTE: </b> All number values in pennies
 * 
 * @author andrejs.burenins
 */
public interface IStockService {
	
	/**
	 * @param stock {@link Stock} 
	 * @param price (in pennies)
	 * @return Calculated Dividend Yield or {@code null} if stock's price is {@code 0}
	 */
	BigDecimal calculateDividendYield(final Stock stock, BigDecimal price);
	
	/**
	 * @param stock {@link Stock} 
	 * @param price (in pennies)
	 * @return Calculated P/E Ratio or {@code null} if {@link #calculateDividendYield(Stock)} returns {@code null} or {@code 0}
	 */
	BigDecimal calculatePERatio(final Stock stock, BigDecimal price);
	
	
	/**
	 * @param stock {@link Stock} 
	 * @return calculated Volume Weighted Stock Price based on trades in past 15 minutes
	 */
	BigDecimal calculateVolumeWeightedPrice(final Stock stock);
	
	/**
	 * @return GBCE All Share Index using the geometric mean of prices for all stocks, or {@code null} if no trade available
	 */
	BigDecimal calculateGBCEAllShareIndex();
	
	/**
	 * Records a trade, with timestamp, quantity of shares, buy or sell indicator and traded price 
	 * @param stock {@link Stock} 
	 * @param quantity quantity of shares
	 * @param side buy or sell {@link TradeSide indicator} 
	 * @param price (in pennies)
	 * @return {@link Trade recorded trade}
	 */
	Trade executeTrade(final Stock stock, int quantity, TradeSide side, BigDecimal price);
	
	/**
	 * @param stock {@link Stock} 
	 * @return List of {@link Trade}s for specified {@link Stock}. Never {@code null}, empty list if there are no one
	 */
	List<Trade> findTrades(final Stock stock);
	
	/**
	 * @return snapshot of current trades
	 */
	Map<Stock, List<Trade>> tradesSnapshot();
	
	/**
	 * @return lookup map of predefined stocks by stock's symbol keys
	 */
	Map<Symbol, Stock> getPredefinedSocks();
}
