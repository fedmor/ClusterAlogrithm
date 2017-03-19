/**
 * 
 */
package org.alogrithm.K_means;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mor
 *
 */
public class KMtest {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		KMeans kmeans = new KMeans(3);

		List<double[]> DataSet = new ArrayList<double[]>();
		List<ArrayList<double[]>> clusters = new ArrayList<ArrayList<double[]>>();

		String filePath = "/Users/Mor/Documents/MachineLearningData/BpData-iris/iris.data.txt";
		LoadData(DataSet, filePath);
		kmeans.K_Means(5, DataSet);
		clusters.addAll(kmeans.getCluters());
		OutPutClusters(clusters);
		

	}

	private static void OutPutClusters(List<ArrayList<double[]>> clusters) {

		FileWriter fw = null;
		try {
			fw = new FileWriter("/Users/Mor/Documents/MachineLearningData/BpData-iris/ClustersResult.txt");
			for (int i = 0; i < clusters.size(); i++) {
				fw.write((i + 1) + LINE_SEPARATOR);
				for (int j = 0; j < clusters.get(i).size(); j++) {
					for (int p = 0; p < clusters.get(i).get(j).length - 1; p++) {
						fw.write(clusters.get(i).get(j)[p] + ",");
					}
					fw.write(clusters.get(i).get(j)[clusters.get(i).get(j).length - 1] + LINE_SEPARATOR);
				}
				fw.flush();
			}

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			if (fw != null)
				try {
					fw.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
		}

	}

	private static void LoadData(List<double[]> dataSet, String filePath) {

		try {
			String encoding = "GBK";
			File file = new File(filePath);

			if (file.exists() && file.isFile()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				@SuppressWarnings("resource")
				BufferedReader bufferedreader = new BufferedReader(read);
				String linetxt = null;
				while ((linetxt = bufferedreader.readLine()) != null) {
					String a[] = linetxt.split(",");
					double[] Data = new double[4];
					for (int i = 0; i < a.length - 1; i++) {
						if (a[i] != null && !a[i].trim().equals("")) {
							try {
								double value = Double.parseDouble(a[i]);
								Data[i] = value;
							} catch (Exception e) {
								System.out.println("error Message：" + e.getMessage());
							}
						}
					}
					dataSet.add(Data);
				}
			}

		} catch (Exception e) {
			System.out.println("there is none file");
		}

	}

}
