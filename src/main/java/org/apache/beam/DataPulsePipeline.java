/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.beam;

import DataTransformation.ParsingJSON;
import Models.Option;
import Models.PageView;
import Models.PageViewBQSchema;
import org.apache.beam.runners.dataflow.DataflowRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.ValueProvider;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static Models.PageViewBQSchema.PageViewsSchema.getPageViewSchema;
import static helpers.Config.*;

/**
 * A starter example for writing Beam programs.
 *
 * <p>The example takes two strings, converts them to their upper-case
 * representation and logs them.
 *
 * <p>To run this starter example locally using DirectRunner, just
 * execute it without any additional parameters from your favorite development
 * environment.
 *
 * <p>To run this starter example using managed resource in Google Cloud
 * Platform, you should specify the following command-line options:
 * --project=<YOUR_PROJECT_ID>
 * --stagingLocation=<STAGING_LOCATION_IN_CLOUD_STORAGE>
 * --runner=DataflowRunner
 */
public class DataPulsePipeline {

    private static final Logger LOG = LoggerFactory.getLogger(DataPulsePipeline.class);

    public static void main(String[] args) {
        // pipeline options
        Option options = PipelineOptionsFactory.fromArgs(args).withValidation()
                .as(Option.class);

        //creating pipeline
        Pipeline p = Pipeline.create(options);

        // Set Dataflow specific options
        options.setJobName("data-pulse-job"); //job name
        options.setTempLocation(PATH + "/tmp");//cloud storage temp file location
        options.setProject(PROJECT_ID);// GCP project id
        options.setRegion("us-central1");//set region
        options.setStagingLocation(PATH + "/staging");
//        options.setTemplateLocation(PATH + "/template");
        options.setMaxNumWorkers(10);//max workers
        options.setRunner(DataflowRunner.class);//set data flow as runner

        LOG.info("Reading JSON lines");
        PCollection<String> json = p.apply("ReadJSONLines", TextIO.read().from(options.getInputFile())); ///reading JSON lines

        PCollection<PageView> pageViews = json.apply("ParseJson", ParDo.of(new ParsingJSON()))////Parsing JSON to page view schema
                .apply("FilterData", Filter.by((PageView pageview) -> "product".equals((pageview.getPost_type()))));///post type must be a product
//                .apply("EnrichWithCountryInfo", ParDo.of(new EnrichingWithCountryInfo())); //adding country name and code

        //adding page views to bigQuery
        pageViews.apply("ConvertToPageViewsBQ", ParDo.of(new PageViewBQSchema.PageViewsSchema()))
                .apply("WriteToPageViewsBQ", BigQueryIO.writeTableRows()
                        .to(String.format("%s:%s.%s", PROJECT_ID, DATASET_ID, PAGEVIEWS))
                        .withSchema(getPageViewSchema())
                        .withCustomGcsTempLocation(ValueProvider.StaticValueProvider.of(PATH + "/tmpBQ/"))
                        .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
                        .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND));

        p.run();//.waitUntilFinish()
    }
}
