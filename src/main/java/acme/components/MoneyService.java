
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

				String baseUrl = "https://v6.exchangerate-api.com/v6/";
				String apiKey = "4bcc0c3ea0675efdbc6f6b4d";
				String url = baseUrl + apiKey + "/pair/" + sourceCurrency + "/" + targetCurrency;

				record = api.getForObject( //				
					url, ExchangeRate.class);

				assert record != null;
				rate = record.getConversion_rate();
				assert rate != null;
				targetAmount = rate * sourceAmount;

				target = new Money();
				target.setAmount(targetAmount);
				target.setCurrency(targetCurrency);

				moment = new Date();

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

	public MoneyExchange computeMoneyExchange2(final Money source, final String targetCurrency) {
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

				String baseUrl = "https://v6.exchangerate-api.com/v6/";
				String apiKey = "e96c9d778afac9897c670cc9";
				String url = baseUrl + apiKey + "/pair/" + sourceCurrency + "/" + targetCurrency;

				record = api.getForObject( //				
					url, ExchangeRate.class);

				assert record != null;
				rate = record.getConversion_rate();
				assert rate != null;
				targetAmount = rate * sourceAmount;

				target = new Money();
				target.setAmount(targetAmount);
				target.setCurrency(targetCurrency);

				moment = new Date();

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
