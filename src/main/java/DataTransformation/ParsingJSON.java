package DataTransformation;

import Models.PageView;
import com.google.gson.*;
import org.apache.beam.sdk.transforms.DoFn;

public class ParsingJSON extends DoFn<String, PageView> {
    @ProcessElement
    public void processElement(@Element String json, OutputReceiver<PageView> r) throws Exception {
        try {
            Gson gson = new Gson();
            PageView pageview = gson.fromJson(json, PageView.class);
            r.output(pageview);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}