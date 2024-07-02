package DataTransformation;

import Models.PageView;
import com.google.gson.*;
import org.apache.beam.DataPulsePipeline;
import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsingJSON extends DoFn<String, PageView> {
    private static final Gson gson = new Gson();
    private static final Pattern pricePattern = Pattern.compile("\\$(\\d+(\\.\\d{1,2})?)\\sUSD");
    private static final Logger LOG = LoggerFactory.getLogger(ParsingJSON.class);

    @ProcessElement
    public void processElement(@Element String json, OutputReceiver<PageView> r) throws Exception {
        LOG.info("Parsing JSON to pageViews");
        try {
            ///mapping the JSON fields to pageView
            PageView pageview = gson.fromJson(json, PageView.class);

            pageview.setPost_discount_price(extractNumericValue(pageview.getPost_discount_price())); //get numeric post discount price
            pageview.setPost_base_price(extractNumericValue(pageview.getPost_base_price()));//get numeric post base price

            r.output(pageview);

        } catch (Exception e) {
            LOG.error(String.valueOf(e));
        }
    }

    //this function is used to extract the numeric price value from string prices having $ and USD
    private String extractNumericValue(String priceString) {
        Matcher matcher = pricePattern.matcher(priceString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "0";  // Default to 0 if no match found
    }
}