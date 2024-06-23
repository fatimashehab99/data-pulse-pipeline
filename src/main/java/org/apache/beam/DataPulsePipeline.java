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

import DataTransformation.PageViewsTransformation;
import Models.Option;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

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

        //reading data from json
        PCollection<FileIO.ReadableFile> jsonLines = p.apply("ReadJSONFile", FileIO.match().filepattern(options.getInputFile()))
                .apply("ParseJSON", FileIO.readMatches());
        //transforming json data to page view objects
        PCollection<String> pageViews = jsonLines.apply("TransformData", ParDo.of(new PageViewsTransformation()));
        pageViews.apply("logResults", ParDo.of(new DoFn<String, Void>() {
            @ProcessElement
            public void processElement(ProcessContext c) {
                LOG.info("Result" + c.element());
            }
        }));
        p.run();
    }

}
