package org.assignments.stockExercise;

import camundajar.impl.com.google.gson.JsonObject;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service("priceAndChangeAdapter")
public class PriceAndChangeHandler implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String symbol = (String) delegateExecution.getVariable("symbol");
        String uri = String.format("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=2GD5U71EEQA6K68O", symbol);
        JSONObject jsonObjectWrapper = HttpHelper.SendRequest(uri);
        JSONObject jsonObjectResult = jsonObjectWrapper.getJSONObject("Global Quote");

        String change = (String) jsonObjectResult.get("09. change");
        String price = (String) jsonObjectResult.get("05. price");
        delegateExecution.setVariable("change", change);
        delegateExecution.setVariable("price", price);

        if(Float.parseFloat(price) < 1 && Float.parseFloat(change) < 0)
            delegateExecution.setVariable("approved", true);
        else{
            delegateExecution.setVariable("approved", false);
            System.out.println("Sell is not approved");
        }


        System.out.println("Change = " + (String) delegateExecution.getVariable("change"));
        System.out.println("Price = " + (String) delegateExecution.getVariable("price"));

    }
}
