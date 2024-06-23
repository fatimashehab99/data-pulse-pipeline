package DataTransformation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.transforms.DoFn;

import java.util.ArrayList;
import java.util.List;

public class PageViewsTransformation extends DoFn<FileIO.ReadableFile, String> {
    @ProcessElement
    public void processElement(ProcessContext c) {
        FileIO.ReadableFile file = c.element();
        try {
            //reading from json
            assert file != null;
            String json = file.readFullyAsUTF8String();
            JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();

            //looping over the array of json
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                //get data
                String productId = jsonObject.get("post_url").getAsString();
                c.output(productId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}