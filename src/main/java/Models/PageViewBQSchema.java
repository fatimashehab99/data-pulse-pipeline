package Models;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import static helpers.Config.*;

public class PageViewBQSchema {
    public static class PageViewsSchema extends DoFn<PageView, TableRow> {
        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        private static final Logger LOG = LoggerFactory.getLogger(PageViewsSchema.class);

        @ProcessElement
        public void processElement(ProcessContext c) throws ParseException {
            PageView pageview = c.element();
            assert pageview != null;
            try {
                LOG.info("Adding pageViews to bigQuery");
                TableRow row = new TableRow()
                        .set(POST_TITLE, pageview.getPost_title())
                        .set(POST_TAGS, pageview.getPost_tags())
                        .set(POST_URL, pageview.getPost_url())
                        .set(POST_TYPE, pageview.getPost_type())
                        .set(DOMAIN, pageview.getDomain())
                        .set(POST_DESCRIPTION, pageview.getDescription())
                        .set(POST_IMAGE, pageview.getPost_image())
                        .set(POST_CURRENCY, pageview.getPost_currency())
                        .set(POST_DISCOUNT_PRICE, parseStringToDouble(pageview.getPost_discount_price()))
                        .set(POST_BASE_PRICE, parseStringToDouble(pageview.getPost_base_price()))
                        .set(POST_VENDOR, pageview.getPost_vendor())
                        .set(POST_DATE, dateFormat.parse(pageview.getPost_date()))
                        .set(POST_ID, pageview.getPost_id())
                        .set(POST_ITEMS, pageview.getPost_items())
                        .set(IP, pageview.getIp())
                        .set(USER_ID, pageview.getUser_id())
                        .set(DEVICE, pageview.getDevice())

                        .set(COUNTRY_NAME, pageview.getCountry_name())
                        .set(COUNTRY_CODE, pageview.getCountry_code());

                c.output(row);
            } catch (Exception e) {
                LOG.error(String.valueOf(e));
            }

        }

        ///pageView bigQuery Schema
        public static TableSchema getPageViewSchema() {
            List<TableFieldSchema> fields = new ArrayList<>();
            // Define each field with its name and type
            fields.add(new TableFieldSchema().setName(POST_TITLE).setType("STRING"));
            fields.add(new TableFieldSchema().setName(POST_TAGS).setType("STRING").setMode("REPEATED")); // Assuming post_tags is an array of strings
            fields.add(new TableFieldSchema().setName(POST_URL).setType("STRING"));
            fields.add(new TableFieldSchema().setName(POST_TYPE).setType("STRING"));
            fields.add(new TableFieldSchema().setName(DOMAIN).setType("STRING"));
            fields.add(new TableFieldSchema().setName(POST_DESCRIPTION).setType("STRING"));
            fields.add(new TableFieldSchema().setName(POST_IMAGE).setType("STRING"));
            fields.add(new TableFieldSchema().setName(POST_CURRENCY).setType("STRING"));
            fields.add(new TableFieldSchema().setName(POST_DISCOUNT_PRICE).setType("FLOAT"));
            fields.add(new TableFieldSchema().setName(POST_BASE_PRICE).setType("FLOAT"));
            fields.add(new TableFieldSchema().setName(POST_VENDOR).setType("STRING"));
            fields.add(new TableFieldSchema().setName(POST_DATE).setType("TIMESTAMP"));
            fields.add(new TableFieldSchema().setName(POST_ID).setType("STRING"));
            fields.add(new TableFieldSchema().setName(POST_ITEMS).setType("STRING").setMode("REPEATED")); // Assuming post_items is an array of strings
            fields.add(new TableFieldSchema().setName(IP).setType("STRING"));
            fields.add(new TableFieldSchema().setName(USER_ID).setType("STRING"));
            fields.add(new TableFieldSchema().setName(DEVICE).setType("STRING"));
            fields.add(new TableFieldSchema().setName(COUNTRY_NAME).setType("STRING"));
            fields.add(new TableFieldSchema().setName(COUNTRY_CODE).setType("STRING"));
            return new TableSchema().setFields(fields);
        }

        private Double parseStringToDouble(String numericString) {
            if (numericString != null) {
                try {
                    return Double.parseDouble(numericString.trim());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null; // Handle null case if needed
        }
    }
}
