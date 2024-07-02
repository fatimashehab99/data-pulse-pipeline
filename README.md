# Data Pulse

## Overview
Data Pulse is a pipeline designed to process and transform pageviews scraped from a web scraper. Utilizing Apache Beam and Google Cloud Dataflow, the pipeline reads, parses, filters, enriches, and writes web-scraped data to Google BigQuery, ensuring efficient storage and analysis.

## Features
- **JSON Parsing**: Reads JSON lines and converts them into `PageView` objects.
- **Data Filtering**: Retains only pageviews with a post type of "product".
- **Data Enrichment**: Adds country information based on the user's IP address using the MaxMind GeoLite2 database.
- **BigQuery Integration**: Writes processed data to Google BigQuery.
- **Robust Logging and Error Handling**: Ensures data integrity throughout the pipeline.

## Setup

### Prerequisites
- Java 8 or higher
- Apache Beam SDK
- Google Cloud SDK
- Access to Google Cloud Platform with BigQuery and Cloud Storage

### Installation
1. **Clone the repository**
    ```bash
    git clone https://github.com/yourusername/data-pulse.git
    cd data-pulse
    ```

2. **Set up your Google Cloud environment**
    ```bash
    gcloud init
    gcloud auth application-default login
    ```

3. **Modify configuration files**
   Update the `helpers/Config.java` file with your GCP project ID, dataset ID, and Cloud Storage path.

### Running the Pipeline
To run the pipeline locally or on Google Cloud Dataflow, use the following command:

```bash
mvn compile exec:java -Dexec.mainClass=org.apache.beam.DataPulsePipeline -Dexec.args="--project=<YOUR_PROJECT_ID> --stagingLocation=gs://<YOUR_BUCKET>/staging --tempLocation=gs://<YOUR_BUCKET>/tmp --runner=DataflowRunner --inputFile=gs://<YOUR_BUCKET>/input/input.json"
