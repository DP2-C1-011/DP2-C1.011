
package acme.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import acme.client.data.datatypes.Money;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.StringHelper;
import acme.entities.SystemConfiguration;
import acme.form.MoneyExchange;

@Service
public class MoneyService {

	@Autowired
	private SystemCurrencyRepository repository;


	public boolean checkContains(final String currency) {
		final SystemConfiguration configuration = this.repository.getSystemConfiguration().get(0);
		final String acceptedCurrenciesStr = configuration.getAcceptedCurrencies();
		final List<String> currencies = new ArrayList<>();
		for (final String currencyStr : acceptedCurrenciesStr.split(","))
			currencies.add(currencyStr);
		return currencies.contains(currency);
	}

	public MoneyExchange computeMoneyExchange(final Money source, final String targetCurrency) {
		assert source != null;
		assert !StringHelper.isBlank(targetCurrency);

		MoneyExchange result = null;
		RestTemplate api;
		ExchangeRate record;
		String sourceCurrency, key;
		Double sourceAmount, targetAmount, rate;
		Money target;
		Date moment;
		if (!source.getCurrency().equals(targetCurrency))
			try {
				api = new RestTemplate();

				sourceCurrency = source.getCurrency();
				sourceAmount = source.getAmount();

				record = api.getForObject( //				
					"http://apilayer.net/api/live?source={0}&currencies={1}&access_key={2}&format=1", //
					ExchangeRate.class, //
					sourceCurrency, //
					targetCurrency, //
					"63259d4389193b9554785f5cea807177");

				assert record != null;
				key = String.format("%s%s", sourceCurrency, targetCurrency);
				rate = record.getQuotes().get(key);
				assert rate != null;
				targetAmount = rate * sourceAmount;

				target = new Money();
				target.setAmount(targetAmount);
				target.setCurrency(targetCurrency);

				moment = new Date(record.getTimestamp() * 1000L);

				result = new MoneyExchange();
				result.setSource(source);
				result.setTargetCurrency(targetCurrency);
				result.setDate(moment);
				result.setTarget(target);

				MomentHelper.sleep(1000); // HINT: need to pause the requests to the API a bit down to prevent DOS attacks
			} catch (final Throwable oops) {
				result = null;
			}
		else {
			result = new MoneyExchange();
			result.setSource(source);
			result.setTargetCurrency(targetCurrency);
			result.setDate(new Date());
			result.setTarget(source);
		}
		return result;
	}

}
