---
title: Programming Exercises
layout: page
permalink: /exercises/
---

This training includes a few programming exercises that teach how to  implement scalable data analysis programs with Apache Flink's APIs and libraries. At this time, the exercises focus on Flink's DataSet API (batch) but will be extended to further APIs and libraries in the future.

The programming exercises assume a [working development environment](/setup.html) and some basic knowledge of Flink's programming primitives.

### DataSet API Exercises

The [DataSet API](http://ci.apache.org/projects/flink/flink-docs-master/apis/programming_guide.html) is a programming model for scalable batch processing. It features a Java and a Scala API which are feature equivalent and very similar. 

#### Exercise 1: Mail Statistics

Count the number of mails in the archive of Flink's developer mailing list per email address and month.

| **Instructions**				| [Exercise 1: Mail Statistics](/exercises/mailStats.html)
| **Data Set**                  | [Mail Data Set](/exercises/trainingData.html) |
| **API Features** &nbsp;&nbsp; | [Map](http://ci.apache.org/projects/flink/flink-docs-master/apis/dataset_transformations.html#map), [GroupBy](http://ci.apache.org/projects/flink/flink-docs-master/apis/dataset_transformations.html#transformations-on-grouped-dataset), [GroupReduce](http://ci.apache.org/projects/flink/flink-docs-master/apis/dataset_transformations.html#groupreduce-on-grouped-dataset) |
| **Sample Solution** 			| ... |

<br>

#### Exercise 2: TF-IDF

Compute TermFrequency-InvertedDocumentFrequency (TF-IDF) metrics for words in all mails on the Flink developer mailing list. TF-IDF is a measure for the importance of a word in a document and commonly used by search engine for result ranking.

| **Instructions**				| [Exercise 2: TF-IDF](/exercises/tfidf.html)
| **Data Set**                  | [Mail Data Set](/exercises/trainingData.html) |
| **API Features** &nbsp;&nbsp; | [FlatMap](http://ci.apache.org/projects/flink/flink-docs-master/apis/dataset_transformations.html#flatmap), [GroupBy](http://ci.apache.org/projects/flink/flink-docs-master/apis/dataset_transformations.html#transformations-on-grouped-dataset), [GroupReduce](http://ci.apache.org/projects/flink/flink-docs-master/apis/dataset_transformations.html#groupreduce-on-grouped-dataset), [Join](http://ci.apache.org/projects/flink/flink-docs-master/apis/dataset_transformations.html#join), DataSet.collect() |
| **Sample Solution** 			| ... |

<br>

### Table API

The Table API focuses on the processing of structured data and has a syntax similar to SQL. The Table API can be mixed with the DataSet API and the DataStream API and offers methods to convert a DataSet/DataStream into a Table and vice versa.

#### Exercise 3: Mail Statistics with Table API

Same exercise as [Exercise 1](/exercises/mailStats.html), but using the Table API to compute the mail statistics.

| **Instructions**				| Exercise 3: Mail Statistics with Table API
| **Data Set**                  | [Mail Data Set](/exercises/trainingData.html) |
| **API Features** &nbsp;&nbsp; | [Map](http://ci.apache.org/projects/flink/flink-docs-master/apis/dataset_transformations.html#map), Table.select(), Table.groupBy(), Table.count() |
| **Sample Solution** 			| ... |

<br>

#### Exercise 4: TF-IDF with Table API

Same exercise as [Exercise 2](/exercises/tfidf.html), but using the Table API to compute the TF-IDF scores.

| **Instructions**				| Exercise 4: TF-IDF with Table API
| **Data Set**                  | [Mail Data Set](/exercises/trainingData.html) |
| **API Features** &nbsp;&nbsp; | [FlatMap](http://ci.apache.org/projects/flink/flink-docs-master/apis/dataset_transformations.html#flatmap), Table.groupBy(), Table.count(), Table.join(), DataSet.collect() |
| **Sample Solution** 			| ... |

<br>

### Bonus Exercises

#### Bonus 1: DataStream API?

#### Bonus 2: Iterations?

#### Bonus 3: Gelly?