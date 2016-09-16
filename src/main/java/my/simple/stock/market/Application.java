package my.simple.stock.market;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import my.simple.stock.market.Stock.Symbol;
import my.simple.stock.market.Trade.TradeSide;

/**
 * @see StockServiceTest
 * @author andrejs.burenins
 */
public class Application {
	
	private static IStockService stockService;

	public static void main(String[] args) {
		
		stockService = new StockService();
		
		try (Scanner sc = new Scanner(System.in)) {
			String line = null;
			while (true) {
				System.out.print("Enter the Stock (TEA, POP, ALE, GIN or JOE) or DONE to exit: ");
				line = sc.nextLine().trim().toUpperCase();
				if ("DONE".equals(line)){
					System.out.println("Bye!");
					return;
				}
				Stock stock = null;
				try {
					Symbol symbol = Stock.Symbol.valueOf(line);
					stock = stockService.getPredefinedSocks().get(symbol);
			    } catch (IllegalArgumentException iae) {
			    	System.out.format("Unable to find Stock by symbol: %s%n", line);
			        continue;
			    }
				BigDecimal price = null;
				while(price == null){
					System.out.print("Enter the positive price (e.g. 56 or 1.12345) : ");
					line = sc.nextLine().trim();
					
					try {
						price = new BigDecimal(line);
						if (BigDecimal.ZERO.compareTo(price) >= 0) {
							price = null;
							throw new NumberFormatException();
						}
					} catch (NumberFormatException error) {
						System.out.format("Unable to parse price or price is not positive: %s%n", line);
						continue;
					}
				}
				System.out.format("Dividend Yield: %1$,.5f%n", stockService.calculateDividendYield(stock, price));
				System.out.format("P/E Ratio: %1$,.5f%n", stockService.calculatePERatio(stock, price));
				
				int quantity = 0;
				while(quantity <= 0){
					System.out.print("Trade. Enter the positive integer quantity of shares (e.g. 56 or 1000) : ");
					line = sc.nextLine().trim();
					try {
						quantity = Integer.parseInt(line);
						if (quantity <= 0) {
							throw new NumberFormatException();
						}
					} catch (NumberFormatException error) {
						System.out.format("Unable to parse quantity of shares or quantity is not positive: %s%n", line);
						continue;
					}
				}
				
				TradeSide side = null;
				while (side == null) {
					System.out.print("Trade. Enter the buy or sell indicator (BUY or SELL) : ");
					line = sc.nextLine().trim().toUpperCase();
					try {
						side = TradeSide.valueOf(line);
				    } catch (IllegalArgumentException iae) {
				    	System.out.format("Unable to find TradeSide by value: %s%n", line);
				        continue;
				    }
				}
				Trade trade = stockService.executeTrade(stock, quantity, side, price);
				System.out.format("Trade executed: %s%n", trade);
				System.out.format("Volume Weighted Stock Price: %1$,.5f%n", stockService.calculateVolumeWeightedPrice(stock));
				System.out.format("GBCE All Share Index: %1$,.5f%n%n%n", stockService.calculateGBCEAllShareIndex());
				
				System.out.println("Current trades snapshot:");
				Map<Stock, List<Trade>> snapshot = stockService.tradesSnapshot();
				snapshot.entrySet()
					.stream()
					.forEach(e -> {
						System.out.format("	Stock  %s:%n", e.getKey().getSymbol());
						e.getValue()
						.stream()
						.forEach(t -> {
							System.out.format("		%s%n", t.toString());
						});
					});
				System.out.println("------------------------------------------------------------------------------------");
			}
		}
	}

	
}
