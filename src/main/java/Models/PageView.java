package Models;

import org.apache.beam.sdk.schemas.JavaFieldSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.util.List;
import java.util.Objects;

@DefaultSchema(JavaFieldSchema.class)
public class PageView {

    public String post_title;
    public List<String> post_tags;
    public String post_url;
    public String post_type;
    public String domain;
    public String description;
    public String post_image;
    public String post_currency;
    public String post_discount_price;
    public String post_base_price;
    public String post_vendor;
    public String post_date;
    public String post_id;
    public List<String> post_items;
    public String ip;
    public String user_id;
    public String device;
    public @javax.annotation.Nullable String country_name;
    public @javax.annotation.Nullable String country_code;

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public List<String> getPost_tags() {
        return post_tags;
    }

    public void setPost_tags(List<String> post_tags) {
        this.post_tags = post_tags;
    }

    public String getPost_url() {
        return post_url;
    }

    public void setPost_url(String post_url) {
        this.post_url = post_url;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getPost_currency() {
        return post_currency;
    }

    public void setPost_currency(String post_currency) {
        this.post_currency = post_currency;
    }

    public String getPost_discount_price() {
        return post_discount_price;
    }

    public void setPost_discount_price(String post_discount_price) {
        this.post_discount_price = post_discount_price;
    }

    public String getPost_base_price() {
        return post_base_price;
    }

    public void setPost_base_price(String post_base_price) {
        this.post_base_price = post_base_price;
    }

    public String getPost_vendor() {
        return post_vendor;
    }

    public void setPost_vendor(String post_vendor) {
        this.post_vendor = post_vendor;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public List<String> getPost_items() {
        return post_items;
    }

    public void setPost_items(List<String> post_items) {
        this.post_items = post_items;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageView pageView = (PageView) o;
        return Objects.equals(post_title, pageView.post_title) && Objects.equals(post_tags, pageView.post_tags) && Objects.equals(post_url, pageView.post_url) && Objects.equals(post_type, pageView.post_type) && Objects.equals(domain, pageView.domain) && Objects.equals(description, pageView.description) && Objects.equals(post_image, pageView.post_image) && Objects.equals(post_currency, pageView.post_currency) && Objects.equals(post_discount_price, pageView.post_discount_price) && Objects.equals(post_base_price, pageView.post_base_price) && Objects.equals(post_vendor, pageView.post_vendor) && Objects.equals(post_date, pageView.post_date) && Objects.equals(post_id, pageView.post_id) && Objects.equals(post_items, pageView.post_items) && Objects.equals(ip, pageView.ip) && Objects.equals(user_id, pageView.user_id) && Objects.equals(device, pageView.device) && Objects.equals(country_name, pageView.country_name) && Objects.equals(country_code, pageView.country_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post_title, post_tags, post_url, post_type, domain, description, post_image, post_currency, post_discount_price, post_base_price, post_vendor, post_date, post_id, post_items, ip, user_id, device, country_name, country_code);
    }

    @Override
    public String toString() {
        return "PageView{" +
                "post_title='" + post_title + '\'' +
                ", post_tags=" + post_tags +
                ", post_url='" + post_url + '\'' +
                ", post_type='" + post_type + '\'' +
                ", domain='" + domain + '\'' +
                ", description='" + description + '\'' +
                ", post_image='" + post_image + '\'' +
                ", post_currency='" + post_currency + '\'' +
                ", post_discount_price='" + post_discount_price + '\'' +
                ", post_base_price='" + post_base_price + '\'' +
                ", post_vendor='" + post_vendor + '\'' +
                ", post_date='" + post_date + '\'' +
                ", post_id='" + post_id + '\'' +
                ", post_items=" + post_items +
                ", ip='" + ip + '\'' +
                ", user_id='" + user_id + '\'' +
                ", device='" + device + '\'' +
                ", country_name='" + country_name + '\'' +
                ", country_code='" + country_code + '\'' +
                '}';
    }
}
