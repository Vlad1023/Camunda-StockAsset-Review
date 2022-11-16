package org.assignments.stockExercise;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service("symbolAdapter")
public class SymbolHandler implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String stockName = (String) delegateExecution.getVariable("stock");
        System.out.println("stock name = " + stockName);
        String uri = String.format("https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=%s&apikey=2GD5U71EEQA6K68O", stockName);
        JSONObject result = HttpHelper.SendRequest(uri);
        JSONArray jsonArray = result.getJSONArray("bestMatches");
        JSONObject firstResult = (JSONObject) (jsonArray.get(0));
        delegateExecution.setVariable("symbol", firstResult.get("1. symbol"));

    }
    }
