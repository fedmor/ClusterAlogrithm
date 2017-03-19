/**
 * 
 */
package org.alogrithm.K_means;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Mor
 *
 */
public class KMeans {

	private int k;
	private int times;
	private int[] centersLocation;
	private ArrayList<double[]> NewCenters;
	private List<double[]> DataSet;
	private ArrayList<ArrayList<double[]>> clusters;
	// private ArrayList<ArrayList<double[]>> CopyClusters;
	private ArrayList<double[]> err;
	private Random random;

	public KMeans(int k) { // 构造函数初始化
		if (k < 0)
			this.k = 1;
		else
			this.k = k;
		random = new Random();
		centersLocation = new int[k];
		NewCenters = new ArrayList<double[]>();
		DataSet = new ArrayList<double[]>();
		clusters = new ArrayList<ArrayList<double[]>>();
		// CopyClusters = new ArrayList<ArrayList<double[]>>();
		InitClusters();
		// InitCopyClusters();
	}

	/*
	 * private void InitCopyClusters() { for (int i = 0; i < k; i++) {
	 * 
	 * CopyClusters.add(new ArrayList<double[]>()); } }
	 */

	private void InitClusters() {
		for (int i = 0; i < k; i++) {

			clusters.add(new ArrayList<double[]>());
		}
	}

	public void K_Means(int times, List<double[]> DataSet) { // 函数对外调用方法
		this.times = times;
		this.DataSet = DataSet;
		SetCenterLocation();
		CalculateCluster();
		RefinementClusters();
	}

	private void CalculateCluster() {
		double[] Distance = new double[k];
		for (int i = 0; i < DataSet.size(); i++) {
			for (int j = 0; j < k; j++)
				Distance[j] = calculateDistance(DataSet.get(centersLocation[j]), DataSet.get(i));
			int miniLocation = GetMiniLocation(Distance);

			clustersAdd(miniLocation, DataSet.get(i));

		}
	}

	private void RefinementClusters() {
		// CopyClusters.clear();
		// CopyClusters.addAll(clusters);
		for (int i = 0; i < times; i++) {

			UpdataCenters();
			// NewCenters();
			UpdataCluster();

		}

	}

	/*
	 * private void UpdataCluster() { clusters.clear();
	 * clusters.addAll(CopyClusters);
	 * 
	 * }
	 */

	private void UpdataCluster() {
		double[] Distance = new double[k];
		// CopyClusters.clear();
		// InitCopyClusters();
		for (int i = 0; i < DataSet.size(); i++) {
			for (int j = 0; j < k; j++) {
				Distance[j] = calculateDistance(NewCenters.get(j), DataSet.get(i));
			}
			int miniLocation = GetMiniLocation(Distance);
			clustersAdd(miniLocation, DataSet.get(i));
		}
	}

	/*
	 * private void CopyClustersAdd(int miniLocation, double[] ds) {
	 * 
	 * CopyClusters.get(miniLocation).add(ds); }
	 */

	private void UpdataCenters() {
		for (int i = 0; i < k; i++) {
			double[] ave = new double[4];
			for (int j = 0; j < 4; j++) {
				double sum = 0d;
				for (int p = 0, len_p = clusters.get(i).size(); p < len_p; p++) {
					sum = sum + clusters.get(i).get(p)[j];
				}
				ave[j] = sum / clusters.get(i).size();
			}
			NewCenters.add(ave);
		}
		clusters.clear();
		InitClusters();
	}

	public List<ArrayList<double[]>> getCluters() {
		List<ArrayList<double[]>> temp = new ArrayList<ArrayList<double[]>>();
		temp.addAll(clusters);
		return temp;
	}

	private void clustersAdd(int miniLocation, double[] ds) {

		clusters.get(miniLocation).add(ds);
	}

	private int GetMiniLocation(double[] distance) {
		double miniValue = Integer.MAX_VALUE;
		int miniLaction = 0;
		for (int i = 0; i < distance.length; i++) {
			if (miniValue > distance[i]) {
				miniValue = distance[i];
				miniLaction = i;
			}
		}

		return miniLaction;
	}

	private double calculateDistance(double[] ds, double[] ds2) {
		double result = 0d;
		for (int i = 0; i < 4; i++) {
			result += (ds[i] - ds2[i]) * (ds[i] - ds2[i]);
		}
		result = Math.sqrt(result);

		return result;
	}

	private void SetCenterLocation() {
		int[] random = new int[k];
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k; j++) {
				int temp = this.random.nextInt(DataSet.size());
				if (random[j] == temp)
					break;
				else
					random[j] = temp;
			}
		}

		for (int i = 0; i < k; i++)
			centersLocation[i] = random[i];
	}
}
