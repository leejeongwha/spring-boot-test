package com.naver.test.movie.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class RecommendService {
	private DataModel dataModel;

	public List<Integer> itemBasedRecommend(Integer itemId, Integer count) throws Exception {
		List<Integer> itemList = new ArrayList<Integer>();
		ItemSimilarity similarity = new LogLikelihoodSimilarity(this.dataModel);

		GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, similarity);

		List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemId, count);

		for (RecommendedItem recommendedItem : recommendations) {
			itemList.add(Integer.parseInt(recommendedItem.getItemID() + ""));
		}

		return itemList;
	}

	@PostConstruct
	public void loadData() throws Exception {
		FastByIDMap<Collection<Preference>> data = new FastByIDMap<Collection<Preference>>();

		ClassPathResource resource = new ClassPathResource("u.data");
		BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
		String line;
		while ((line = br.readLine()) != null) {
			String[] values = line.split("\\t", -1);
			Long userId = Long.parseLong(values[0]);
			Long itemId = Long.parseLong(values[1]);
			Float value = Float.parseFloat(values[2]);

			if (data.get(userId) == null) {
				List<Preference> row = new ArrayList<Preference>();
				row.add(new GenericPreference(userId, itemId, value));
				data.put(userId, row);
			} else {
				List<Preference> list = (List<Preference>) data.get(userId);
				list.add(new GenericPreference(Long.parseLong(values[0]), itemId, value));
			}
		}
		br.close();

		this.dataModel = new GenericDataModel(GenericDataModel.toDataMap(data, true));
	}
}
