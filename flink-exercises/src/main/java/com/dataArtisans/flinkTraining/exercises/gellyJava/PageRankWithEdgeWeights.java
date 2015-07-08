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

package com.dataArtisans.flinkTraining.exercises.gellyJava;


import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.graph.Edge;
import org.apache.flink.graph.EdgeDirection;
import org.apache.flink.graph.Graph;
import org.apache.flink.graph.ReduceEdgesFunction;
import org.apache.flink.graph.Vertex;
import org.apache.flink.graph.library.PageRankAlgorithm;
import org.apache.flink.graph.utils.Tuple3ToEdgeMap;

/**
 * 
 * The edges input file is expected to contain one edge per line, with String IDs and double
 * values in the following format:"<sourceVertexID>\t<targetVertexID>\t<edgeValue>".
 *
 * This class is used to create a graph from the input data and then to run a PageRankAlgorithm
 * (present in Flink-gelly graph-library)over it. The algorithm used is a simplified implementation
 * of the actual algorithm; its limitation is that all the pages need to have at least one incoming
 * and one outgoing link for correct results. The vertex-centric algorithm takes as input parameters
 * dampening factor and number of iterations.
 * 
 * 
 */


public class PageRankWithEdgeWeights{
	
	private static boolean fileOutput = false;
	private static final double DAMPENING_FACTOR = 0.85;
	private static String edgeInputPath = null;
	private static String outputPath = null;
	private static int maxIterations = 10;

	
	@SuppressWarnings("serial")
	public static void main(String[] args) throws Exception {
	
		if (args.length == 3) {
			
			fileOutput = true;
			edgeInputPath = args[0];
			outputPath = args[1];
			maxIterations = Integer.parseInt(args[2]);

		} else {
			System.err.println("Usage: <input edges path> <output path> <num iterations>");
			return;
		}

		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		//read the Edge DataSet from the input file
		DataSet<Edge<String, Double>> links = env.readCsvFile(edgeInputPath)
				.fieldDelimiter("\t")
				.lineDelimiter("\n")
				.types(String.class, String.class, Double.class)
				.map(new Tuple3ToEdgeMap<String, Double>());
		

		//create a Graph with vertex values initialized to 1.0
		Graph<String, Double, Double> network = Graph.fromDataSet(links,
				new MapFunction<String, Double>() {
			         public Double map(String value) throws Exception {
			        	 return 1.0;
			        	 }
			         }, env);
		
		
		//for each vertex calculate the total weight of its outgoing edges
		DataSet<Tuple2<String, Double>> sumEdgeWeights = 
				network.reduceOnEdges(new SumWeight(), EdgeDirection.OUT);
					

		// assign the transition probabilities as edge weights:
		//divide edge weight by the total weight of outgoing edges for that source 
		Graph<String, Double, Double> networkWithWeights = network
				.joinWithEdgesOnSource(sumEdgeWeights,
						new MapFunction<Tuple2<Double, Double>, Double>() {
							public Double map(Tuple2<Double, Double> value) {
								return value.f0 / value.f1;
							}
						});
								

		//Now run the Page Rank algorithm over the weighted graph
		DataSet<Vertex<String, Double>> pageRanks = networkWithWeights.run(
				new PageRankAlgorithm<String>(DAMPENING_FACTOR, maxIterations))
				.getVertices();

		
		if (fileOutput) {
			pageRanks.writeAsCsv(outputPath, "\n", "\t");
			// since file sinks are lazy,trigger the execution explicitly
			env.execute();
		} 
		else {
			pageRanks.print();
		}

	}

	
	//function to calculate the total weight of outgoing edges from a node
	@SuppressWarnings("serial")
	static final class SumWeight implements ReduceEdgesFunction<Double> {
			@Override
			public Double reduceEdges(Double firstEdgeValue, Double secondEdgeValue) {
				return firstEdgeValue+secondEdgeValue;
			}
	}

}
