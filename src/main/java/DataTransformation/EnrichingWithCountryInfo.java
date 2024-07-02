package DataTransformation;

import Models.PageView;
import com.google.gson.Gson;
import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import org.apache.beam.sdk.transforms.DoFn;

import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;

public class EnrichingWithCountryInfo extends DoFn<PageView, PageView> {
    @ProcessElement
    public void processElement(@Element PageView pageView, OutputReceiver<PageView> c) throws Exception {
        DatabaseReader reader = null;
        try {
//            InputStream stream = new URL("gs://data-pulse/GeoLite2-City.mmdb").openStream();//Opens a connection to this URL and returns an InputStream for reading from that connection
//            reader = new DatabaseReader.Builder(stream).withCache(new CHMCache()).build();
            String databaseFile = "src/sources/GeoLite2-City.mmdb";
            File database = new File(databaseFile);
            reader = new DatabaseReader.Builder(database).build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String ipAddress = pageView.getIp();
            InetAddress ip = InetAddress.getByName(ipAddress);
            CityResponse response = reader.city(ip);

            // Create a new PageView instance with the updated country information
            PageView enrichedPageView = new PageView();
            enrichedPageView.setPost_title(pageView.getPost_title());
            enrichedPageView.setPost_tags(pageView.getPost_tags());
            enrichedPageView.setPost_url(pageView.getPost_url());
            enrichedPageView.setPost_type(pageView.getPost_type());
            enrichedPageView.setDomain(pageView.getDomain());
            enrichedPageView.setDescription(pageView.getDescription());
            enrichedPageView.setPost_image(pageView.getPost_image());
            enrichedPageView.setPost_currency(pageView.getPost_currency());
            enrichedPageView.setPost_discount_price(pageView.getPost_discount_price());
            enrichedPageView.setPost_base_price(pageView.getPost_base_price());
            enrichedPageView.setPost_vendor(pageView.getPost_vendor());
            enrichedPageView.setPost_date(pageView.getPost_date());
            enrichedPageView.setPost_id(pageView.getPost_id());
            enrichedPageView.setPost_items(pageView.getPost_items());
            enrichedPageView.setIp(pageView.getIp());
            enrichedPageView.setUser_id(pageView.getUser_id());
            enrichedPageView.setDevice(pageView.getDevice());
            enrichedPageView.setCountry_name(response.getCountry().getName());
            enrichedPageView.setCountry_code(response.getCountry().getIsoCode());

            c.output(enrichedPageView);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
