---
layout: page
title: DataSet API - Mail Count
permalink: /exercises/mailCount.html
---

The task of the "Mail Count" exercise is to count the number of emails in the archive of the Flink development mailing list for each unique combination of email address and month. 

### Input Data

This exercise uses the Mail Data Set which was extracted from the Apache Flink development mailing list archive. The [Mail Data Set instructions]({{ site.baseurl }}/exercises/mailData.html) show how to read the data set in a Flink program using the `CsvInputFormat`. 

The task requires two fields, `Timestamp` and `Sender`. The input data can be read as a `DataSet<Tuple2<String, String>>`. When printed, the data set should look similar to this:

~~~
(2014-09-26-08:49:58,Fabian Hueske <fhueske@apache.org>)
(2014-09-12-14:50:38,Aljoscha Krettek <aljoscha@apache.org>)
(2014-09-30-09:16:29,Stephan Ewen <sewen@apache.org>)
~~~

### Expected Output

The result of the task should be a `DataSet<Tuple3<String, String, Integer>>`. The first field specifies the month, the second field the email address, and the third field the number of emails sent to the mailing list by the given email address in the given month. When printed, the data set should looks like:

~~~
(2014-09,fhueske@apache.org,16)
(2014-09,aljoscha@apache.org,13)
(2014-09,sewen@apache.org,24)
(2014-10,fhueske@apache.org,14)
(2014-10,aljoscha@apache.org,17)
~~~

The first line of the example result indicates that 16 mails were sent to the mailing list by `fhueske@apache.org` in September 2014.

### Implementation Hints

#### Program Structure

This exercise is conceptually very similar to the `WordCount` program, which is the standard example to introduce MapReduce. Similar like `WordCount`, this task requires two transformation, `Map` and `Reduce`. 

#### Grouping Keys

In contrast to the `WordCount` program, this exercise requires to group data on two fields (`month` and `email-address`) instead of a single field. Flink's [`DataSet.groupBy()`](http://ci.apache.org/projects/flink/flink-docs-master/apis/programming_guide.html#specifying-keys) transformation, accepts multiple grouping keys and treats them as one composite grouping key.

#### Map Transformation

The [`Map`](http://ci.apache.org/projects/flink/flink-docs-master/apis/dataset_transformations.html#map) transformation is used for record-at-a-time processing and should be used to extract the relevant information from from the input data, i.e., the month from the timestamp field and the email address from the sender field.

#### GroupReduce Transformation

The [`GroupReduce`](http://ci.apache.org/projects/flink/flink-docs-master/apis/dataset_transformations.html#groupreduce-on-grouped-dataset) transformation operates on groups of records and can also be used to count the number of element in a group.

### Reference Solution

Reference solutions are available at GitHub:

- Java: [MailCount.java](https://github.com/dataArtisans/flink-training/blob/master/flink-exercises/src/main/java/com/dataArtisans/flinkTraining/exercises/dataSetJava/mailCount/MailCount.java)
- Scala: [MailCount.scala](https://github.com/dataArtisans/flink-training/blob/master/flink-exercises/src/main/scala/com/dataArtisans/flinkTraining/exercises/dataSetScala/mailCount/MailCount.scala)
