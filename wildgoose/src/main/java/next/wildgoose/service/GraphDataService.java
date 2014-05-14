package next.wildgoose.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import next.wildgoose.dao.DummyData;
import next.wildgoose.dao.ExtractDAO;
import next.wildgoose.dao.HookingKeywordDAO;
import next.wildgoose.dao.NumberOfArticlesDAO;
import next.wildgoose.utility.Uri;

import org.json.JSONObject;

public class GraphDataService implements Daction {
	public DactionResult execute(HttpServletRequest request) {
		ServletContext context = request.getServletContext();
		Uri uri = new Uri(request);
		
		DactionResult result = null;
		JSONObject json = null;
		String apiName = uri.get(4);
		
		if (apiName == null) {
			return null;
		}
		
		Map<String, ExtractDAO> extractMap = new HashMap<String, ExtractDAO>();
		extractMap.put("number_of_articles", (NumberOfArticlesDAO) context.getAttribute("NumberOfArticlesDAO"));
		extractMap.put("number_of_hook_keywords", (HookingKeywordDAO) context.getAttribute("HookingKeywordDAO"));
		extractMap.put("stat_points", (DummyData) context.getAttribute("DummyData"));
		ExtractDAO seledtedApi = extractMap.get(apiName);
		json = seledtedApi.getJson(request);
		
		result = new DactionResult("json", json);
		return result;
	}
}
