package DataTransformation;

import Models.PageView;
import com.google.gson.*;
import org.apache.beam.sdk.transforms.DoFn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsingJSON extends DoFn<String, PageView> {
    private static final Gson gson = new Gson();
    private static final Pattern pricePattern = Pattern.compile("\\$(\\d+(\\.\\d{1,2})?)\\sUSD");

    @ProcessElement
    public void processElement(@Element String json, OutputReceiver<PageView> r) throws Exception {
        try {
            PageView pageview = gson.fromJson(json, PageView.class);

            // Extract numeric value from post_discount_price
            pageview.setPost_discount_price(extractNumericValue(pageview.getPost_discount_price()));
            pageview.setPost_base_price(extractNumericValue(pageview.getPost_base_price()));
            r.output(pageview);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extractNumericValue(String priceString) {
        Matcher matcher = pricePattern.matcher(priceString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "0";  // Default to 0 if no match found
    }
}